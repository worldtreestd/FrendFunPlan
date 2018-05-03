from datetime import datetime
from django.db import models
from django.contrib.auth import get_user_model

User = get_user_model()


class Circle(models.Model):
    """
    圈子
    """
    name = models.CharField(default="", max_length=20, verbose_name='圈子名称', help_text='圈子名称')
    id = models.AutoField(primary_key=True, verbose_name='圈子id', help_text='圈子id')
    address = models.CharField(default="", max_length=50, verbose_name='圈子地点', help_text='圈子地点')
    desc = models.CharField(default='', max_length=200, verbose_name='圈子简介', help_text='圈子简介')
    image = models.ImageField(upload_to="circleimage", verbose_name="图片", null=True, blank=True)
    user = models.ForeignKey(User, max_length=20, verbose_name='创建人', help_text='创建人')
    watch_num = models.IntegerField(default=0, verbose_name='围观次数', help_text='围观次数')
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间', help_text='添加时间')

    class Meta:
        verbose_name = '圈子名'
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.name


class CirclePartnerMessage(models.Model):
    """
    圈友会话
    """
    user = models.ForeignKey(User, verbose_name="圈内用户")
    circle = models.ForeignKey(Circle, verbose_name='所属圈子', help_text='所属圈子', related_name='circle')
    message = models.TextField(default='', verbose_name='聊天消息', help_text='聊天信息')
    type = models.IntegerField(default=0, verbose_name='消息类型', help_text='消息类型')
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')
    user_image_url = models.TextField(default='', verbose_name="用户头像")

    class Meta:
        verbose_name = '圈友会话'
        verbose_name_plural = verbose_name

    @property
    def open_id(self):
        return self.user.open_id

    def __str__(self):
        return self.message


class CircleHotSearch(models.Model):
    """
    圈子热搜
    """
    keyword = models.TextField(default='', max_length=20, verbose_name='关键字', help_text='关键字')
    count = models.IntegerField(default=0, blank=True, null=True, verbose_name='搜索次数', help_text='搜索次数')
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')

    class Meta:
        verbose_name = '圈子搜索'
        verbose_name_plural = verbose_name


class CircleBanner(models.Model):
    """
    圈子轮播
    """
    circle = models.ForeignKey(Circle, verbose_name='所属圈子', help_text='所属圈子')
    add_time = models.DateTimeField(default=datetime.now, verbose_name='添加时间')

    class Meta:
        verbose_name = '圈子轮播'
        verbose_name_plural = verbose_name

    @property
    def circle_name(self):
        return self.circle.name

    @property
    def circle_image_url(self):
        return self.circle.image.url
