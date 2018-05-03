# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2018/2/2 17:17'

from django.db.models.signals import post_save
from django.dispatch import receiver
from .models import CircleHotSearch


@receiver(post_save, sender=CircleHotSearch)
def search_count(sender, instance=None, created=False, **kwargs):
    """
    搜索次数信号量
    :param sender:
    :param created:
    :param instance:
    :param kwargs:
    :return:
    """
    if created:
        keyword = instance
        keyword.count += 1
        keyword.save()
