# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2017/12/28 21:17'

from rest_framework import serializers
from .models import Plan
from circles.models import Circle


class PlanSerializer(serializers.ModelSerializer):

    # 设置当前登录的用户
    user = serializers.CharField(default=serializers.CurrentUserDefault())
    from_circle_name = serializers.ReadOnlyField()
    address = serializers.ReadOnlyField()

    class Meta:
        model = Plan
        fields = ('id', 'from_circle_name', 'from_circle', 'user', 'end_time', 'content', 'address', 'users_num', 'is_finished', 'add_time')
