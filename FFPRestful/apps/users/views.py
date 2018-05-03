from django.contrib.auth.backends import ModelBackend
from django.db.models import Q
from rest_framework import mixins, status
from rest_framework import viewsets
from rest_framework import permissions
from django.contrib.auth import get_user_model
from rest_framework_jwt.serializers import jwt_payload_handler, jwt_encode_handler
from rest_framework.authentication import SessionAuthentication
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.response import Response
from .serializers import UserDetailSerializer, UserRegisterSerializer

User = get_user_model()


class CustomUserBackend(ModelBackend):
    """
    自定义用户验证
    """
    def authenticate(self, request, username=None, password=None, **kwargs):
        try:
            user = User.objects.get(Q(open_id=username) | Q(username=username) | Q(nick_name=username))
            if user.check_password(password):
                return user
        except Exception as e:
            return None


class UserViewSet(mixins.ListModelMixin, mixins.RetrieveModelMixin, mixins.CreateModelMixin,
                  viewsets.GenericViewSet):
    """
    用户相关
    """
    authentication_classes = (JSONWebTokenAuthentication, SessionAuthentication)

    def get_queryset(self):
        return User.objects.filter(username=self.request.user)

    # 动态设置序列化规则
    def get_serializer_class(self):
        if self.action == 'retrieve':
            return UserDetailSerializer
        elif self.action == 'create':

            return UserRegisterSerializer

        return UserDetailSerializer

    # 动态设置权限
    def get_permissions(self):
        if self.action == 'receiver':
            return [permissions.IsAuthenticated()]
        elif self.action == 'create':
            return []
        return []

    # def create(self, request, *args, **kwargs):
    #     serializer = self.get_serializer(data=request.data)
    #     serializer.is_valid(raise_exception=True)
    #     user = self.get_format_suffix(serializer)
    #
    #     re_dict = serializer.data
    #     payload = jwt_payload_handler(user)
    #     re_dict["token"] = jwt_encode_handler(payload)
    #     re_dict["nick_name"] = user.nick_name if user.nick_name else user.username
    #
    #     header = self.get_success_headers(serializer.data)
    #     return Response(re_dict, status=status.HTTP_201_CREATED, headers=header)

    def get_object(self):
        return self.request.user

    # def perform_create(self, serializer):
    #     return serializer.save()

    class Meta:
        model = User
        fields = '__all__'
