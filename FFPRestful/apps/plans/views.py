from .models import Plan
from .serializers import PlanSerializer
from rest_framework import viewsets
from rest_framework import mixins
from rest_framework import permissions
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.authentication import SessionAuthentication
from rest_framework.pagination import PageNumberPagination
from utils.permissions import IsOwnerOrReadOnly


class PlanPagination(PageNumberPagination):
    """
    计划列表分页加载
    """
    page_size = 8
    page_size_query_param = "page_size"
    page_query_param = "page"
    max_page_size = 50


class PlanViewSet(mixins.ListModelMixin, mixins.UpdateModelMixin, mixins.CreateModelMixin, mixins.RetrieveModelMixin, viewsets.GenericViewSet):
    """
    计划详情
    list:
        当前计划列表
    create:
        发布一条计划
    """
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)
    serializer_class = PlanSerializer
    pagination_class = PlanPagination

    def get_queryset(self):
        return Plan.objects.filter(is_finished=False).order_by("-add_time")

    # 动态设置权限
    def get_permissions(self):
        if self.action == 'create' or self.action == 'update':
            return [permissions.IsAuthenticated(), IsOwnerOrReadOnly()]
        elif self.action == 'list':
            return []
        return []
