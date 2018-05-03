from datetime import datetime
from django.db import models
from circles.models import Circle
from django.contrib.auth import get_user_model

User = get_user_model()


class Plan(models.Model):
    from_circle = models.ForeignKey(Circle, verbose_name='来源圈子', related_name='plan_list')
    user = models.ForeignKey(User, verbose_name='发布人', help_text='发布人')
    end_time = models.DateTimeField(default=datetime.now, verbose_name='结束时间', help_text='结束时间')
    content = models.CharField(default='', max_length=200, verbose_name='计划详情', help_text='计划详情')
    address = models.CharField(default='', max_length=50, verbose_name='地点', help_text="地点")
    users_num = models.IntegerField(default=0, verbose_name='参与人数', help_text='参与人数')
    is_finished = models.BooleanField(default=False, verbose_name="是否完成")
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')

    class Meta:
        verbose_name = '计划'
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.content

    @property
    def from_circle_name(self):
        return self.from_circle.name

    @property
    def address(self):
        return self.from_circle.address
