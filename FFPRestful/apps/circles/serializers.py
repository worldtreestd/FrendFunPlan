# _*_ coding:utf-8 _*_
from rest_framework.validators import UniqueValidator

__author__ = 'Legend'
__date__ = '2017/12/28 20:07'

from rest_framework import serializers
from .models import Circle, CirclePartnerMessage, CircleHotSearch, CircleBanner
from plans.serializers import PlanSerializer


class CircleSerializer(serializers.ModelSerializer):
    # 设置当前登录的用户
    user = serializers.CharField(default=serializers.CurrentUserDefault())
    plan_list = PlanSerializer(many=True, read_only=True)

    @staticmethod
    def setup_eager_loading(queryset):
        queryset = queryset.prefetch_related('plan_list')
        return queryset

    class Meta:
        model = Circle
        fields = '__all__'


class CirclePartnerMessageSerializer(serializers.ModelSerializer):
    # 设置当前登录的用户
    user = serializers.CharField(default=serializers.CurrentUserDefault())

    class Meta:
        model = CirclePartnerMessage
        fields = ('circle', 'user', 'open_id', 'message', 'type', 'add_time', 'user_image_url')


class CircleHotSearchSerializer(serializers.ModelSerializer):

    class Meta:
        model = CircleHotSearch
        fields = '__all__'


class CircleBannerDetailSerializer(serializers.ModelSerializer):

    circle = CircleSerializer()

    class Meta:
        model = CircleBanner
        fields = '__all__'


class CircleBannerSerializer(serializers.ModelSerializer):
    circle_name = serializers.ReadOnlyField()
    circle_image_url = serializers.ReadOnlyField()

    class Meta:
        model = CircleBanner
        fields = ('circle', 'add_time', 'circle_name', 'circle_image_url')
