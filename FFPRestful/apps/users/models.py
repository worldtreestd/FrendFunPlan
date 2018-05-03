from django.contrib.auth.models import AbstractUser
from django.db import models


class UserProfile(AbstractUser):
    """
    用户
    """
    nick_name = models.CharField(max_length=30, null=True, blank=True, verbose_name="用户名")
    gender = models.CharField(default='男', choices=(('male', '男'), ('female', '女')), max_length=6)
    email = models.CharField(default='', max_length=30, verbose_name='邮箱')
    open_id = models.CharField(default='', max_length=50)

    class Meta:
        verbose_name = '用户'
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.username

