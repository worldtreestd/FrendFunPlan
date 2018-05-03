# _*_ coding:utf-8 _*_

__author__ = 'Legend'
__date__ = '2017/12/28 21:17'
from .models import ParticipatePlan, ParticipateCircle
from rest_framework import serializers
from rest_framework.validators import UniqueTogetherValidator

from circles.serializers import CircleSerializer
from plans.serializers import PlanSerializer


class ParticipatePlanDetailSerializer(serializers.ModelSerializer):
    plan = PlanSerializer()

    class Meta:
        model = ParticipatePlan
        fields = ('user', 'plan', 'add_time', 'is_finished')


class ParticipatePlanSerializer(serializers.ModelSerializer):

    # 获取当前登录用户
    user = serializers.HiddenField(
        default=serializers.CurrentUserDefault()
    )
    users_num = serializers.ReadOnlyField()
    is_finished = serializers.ReadOnlyField()

    class Meta:
        model = ParticipatePlan
        validators = [
            UniqueTogetherValidator(
                queryset=ParticipatePlan.objects.all(),
                fields=('user', 'plan',),
                message="已参与该计划",
            )
        ]
        fields = ['user', 'plan', 'users_num', 'is_finished']


class ParticipateCircleDetailSerializer(serializers.ModelSerializer):
    circle = CircleSerializer()

    class Meta:
        model = ParticipateCircle
        fields = "__all__"


class ParticipateCircleSerializer(serializers.ModelSerializer):

    user = serializers.HiddenField(
        default=serializers.CurrentUserDefault()
    )

    class Meta:
        model = ParticipateCircle
        validators = [
            UniqueTogetherValidator(
                queryset=ParticipateCircle.objects.all(),
                fields=('user', 'circle'),
                message='已加入该圈子'
            )
        ]
        fields = ['user', 'circle']