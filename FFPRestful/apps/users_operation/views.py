from .serializers import ParticipatePlanDetailSerializer, ParticipatePlanSerializer, ParticipateCircleSerializer, ParticipateCircleDetailSerializer
from .models import ParticipatePlan, ParticipateCircle
from rest_framework import viewsets
from rest_framework import mixins
from rest_framework import filters
from rest_framework.permissions import IsAuthenticated
from utils.permissions import IsOwnerOrReadOnly
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.authentication import SessionAuthentication
from rest_framework_extensions.cache.mixins import CacheResponseMixin


class ParticipatePlanViewSet(mixins.CreateModelMixin, mixins.ListModelMixin, mixins.RetrieveModelMixin,
                             mixins.DestroyModelMixin, mixins.UpdateModelMixin, viewsets.GenericViewSet):
    """
    参与计划
    list:
        已经参与的计划
    create:
        参与计划
    retrieve:
        查询某条计划
    destroy:
        删除某条计划
    """

    permission_classes = (IsAuthenticated, IsOwnerOrReadOnly)
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    serializer_class = ParticipatePlanSerializer
    lookup_field = 'plan_id'
    filter_backends = (filters.SearchFilter,)
    search_fields = ('=plan__is_finished',)

    def get_queryset(self):
        return ParticipatePlan.objects.filter(user=self.request.user).order_by("-add_time")

    # 动态设置序列化规则
    def get_serializer_class(self):
        if self.action == 'list':
            return ParticipatePlanDetailSerializer
        elif self.action == 'create':
            return ParticipatePlanSerializer

        return ParticipatePlanSerializer


class ParticipateCircleViewSet(mixins.ListModelMixin, mixins.CreateModelMixin, mixins.RetrieveModelMixin,
                               viewsets.GenericViewSet):
    """
    加入圈子
    list:
        已经加入的圈子
    create:
        加入圈子
    retrieve:
        查询某个圈子
    """
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    permission_classes = (IsAuthenticated, IsOwnerOrReadOnly)
    lookup_field = 'circle_id'

    def get_queryset(self):
        return ParticipateCircle.objects.filter(user=self.request.user).order_by('-add_time')

    # 动态设置序列化规则
    def get_serializer_class(self):
        if self.action == 'create':
            return ParticipateCircleSerializer
        elif self.action == 'list':
            return ParticipateCircleDetailSerializer

        return ParticipateCircleDetailSerializer
