# _*_ coding:utf-8 _*_
from rest_framework.validators import UniqueValidator

__author__ = 'Legend'
__date__ = '2017/12/30 20:39'
from rest_framework import serializers
from django.contrib.auth import get_user_model
import requests
from FFPRestful.settings import SOCIAL_AUTH_QQ_KEY

User = get_user_model()


class UserDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'username', 'gender')


class UserRegisterSerializer(serializers.ModelSerializer):
    access_token = serializers.CharField(required=True, write_only=True, max_length=50, min_length=32,
                                         label='access_token',
                                         help_text="access_token")
    open_id = serializers.CharField(required=True, max_length=50, write_only=True, min_length=32,
                                    validators=[UniqueValidator(queryset=User.objects.all(), message='当前用户已经存在')],
                                    label='用户唯一标识符', help_text='用户唯一标识符')
    username = serializers.CharField(allow_blank=True, allow_null=True, default="")

    def validate(self, attrs):
        from utils.requestutils import validate_openid
        response = validate_openid(access_token=attrs['access_token'], open_id=attrs['open_id'],)

        del attrs["access_token"]
        attrs['nick_name'] = response['nickname']
        attrs['username'] = response['nickname']
        attrs['gender'] = response['gender']
        attrs['password'] = attrs['open_id']
        return attrs

    class Meta:
        model = User
        fields = ('open_id', 'access_token', 'username')



