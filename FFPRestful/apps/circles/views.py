from rest_framework import viewsets
from rest_framework import mixins
from rest_framework.response import Response
from .models import Circle, CirclePartnerMessage, CircleHotSearch, CircleBanner
from .serializers import CircleSerializer, CirclePartnerMessageSerializer, \
    CircleHotSearchSerializer, CircleBannerSerializer, CircleBannerDetailSerializer
from rest_framework import permissions
from rest_framework import filters
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.authentication import SessionAuthentication
from rest_framework.pagination import PageNumberPagination
from utils.permissions import IsOwnerOrReadOnly
from rest_framework_extensions.cache.mixins import CacheResponseMixin
from rest_framework_extensions.cache.decorators import cache_response


class CirclePagination(PageNumberPagination):
    """
    圈子列表页分页加载
    """
    page_size = 8
    page_size_query_param = "page_size"
    page_query_param = "page"
    max_page_size = 50


class CircleHotSearchPagination(CirclePagination):
    """
    圈子热搜列表页分页加载
    """
    page_size = 12


class CircleViewSet(mixins.ListModelMixin, mixins.CreateModelMixin, mixins.RetrieveModelMixin, viewsets.GenericViewSet):
    """
    圈子详情页
    List:
        当前所有圈子
    create:
        创建圈子
    """
    queryset = Circle.objects.all().order_by('-add_time')
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    serializer_class = CircleSerializer
    pagination_class = CirclePagination
    filter_backends = (filters.SearchFilter,)
    search_fields = ('name', 'desc', 'address', 'id')

    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        instance.watch_num += 1
        instance.save()
        serializer = self.get_serializer(instance)
        return Response(serializer.data)

    def get_queryset(self):
        queryset = Circle.objects.all().order_by('-add_time')
        queryset = self.get_serializer_class().setup_eager_loading(queryset)
        return queryset

    # 动态设置权限
    def get_permissions(self):
        if self.action == 'create':
            return [permissions.IsAuthenticated(), IsOwnerOrReadOnly()]
        elif self.action == 'list' or self.action == 'receiver':
            return []
        return []


class CirclePartnerMessageViewSet(mixins.ListModelMixin, mixins.CreateModelMixin, viewsets.GenericViewSet):
    """
    圈友会话消息
    list:
        当前聊天消息
    create:
        发送一条消息
    """
    queryset = CirclePartnerMessage.objects.all().order_by('add_time')
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    serializer_class = CirclePartnerMessageSerializer
    filter_backends = (filters.SearchFilter,)
    search_fields = ('=circle__id',)

    def get_permissions(self):
        if self.action == 'create':
            return [permissions.IsAuthenticated(), IsOwnerOrReadOnly()]
        elif self.action == 'list':
            return [permissions.IsAuthenticated()]
        return []


class CircleHotSearchViewSet(mixins.ListModelMixin, mixins.CreateModelMixin,
                             mixins.UpdateModelMixin, viewsets.GenericViewSet):
    """
    圈子热搜
    list:
        搜索关键字列表
    create:
        创建一条搜索
    """
    queryset = CircleHotSearch.objects.all().order_by('-count').order_by('-add_time')
    serializer_class = CircleHotSearchSerializer
    pagination_class = CircleHotSearchPagination


class CircleBannerViewSet(mixins.ListModelMixin, mixins.CreateModelMixin, mixins.RetrieveModelMixin, viewsets.GenericViewSet):
    """
    圈子轮播
    list:
        圈子轮播列表
    """
    queryset = CircleBanner.objects.all().order_by('-add_time')
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)

    # 动态设置权限
    def get_permissions(self):
        if self.action == 'create':
            return [permissions.IsAuthenticated(), IsOwnerOrReadOnly()]
        elif self.action == 'list':
            return []
        return []

    # 动态设置序列化规则
    def get_serializer_class(self):
        if self.action == 'create':
            return CircleBannerSerializer
        elif self.action == 'list':
            return CircleBannerDetailSerializer

        return CircleBannerDetailSerializer
