from datetime import datetime
from django.db import models
from django.contrib.auth.models import AbstractUser
from django.contrib.auth import get_user_model
from circles.models import Circle
from plans.models import Plan


User = get_user_model()


class ParticipatePlan(models.Model):
    """
    参与计划
    """
    user = models.ForeignKey(User, verbose_name='用户')
    plan = models.ForeignKey(Plan, verbose_name='加入计划', help_text='加入计划')
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')

    class Meta:
        verbose_name = '参与计划'
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.user.username

    @property
    def users_num(self):
        return self.plan.users_num

    @property
    def is_finished(self):
        return self.plan.is_finished


class ParticipateCircle(models.Model):
    """
    加入圈子
    """
    user = models.ForeignKey(User, verbose_name='用户')
    circle = models.ForeignKey(Circle, verbose_name='加入圈子', help_text='加入圈子')
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')

    class Meta:
        verbose_name = '加入圈子'
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.user.username






