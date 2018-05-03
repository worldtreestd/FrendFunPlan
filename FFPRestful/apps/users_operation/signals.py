# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2017/12/29 21:42'

from django.db.models.signals import post_save, post_delete
from django.dispatch import receiver
from .models import ParticipatePlan


@receiver(post_save, sender=ParticipatePlan)
def participate_plan(sender, instance=None, created=False, **kwargs):
    """
    加入计划信号量
    :param sender:
    :param instance:
    :param created:
    :param kwargs:
    :return:
    """
    if created:
        plan = instance.plan
        plan.users_num += 1
        plan.save()


@receiver(post_delete, sender=ParticipatePlan)
def delete_plan(sender, instance=None, created=False, **kwargs):
    """
    删除计划信号量
    :param sender:
    :param instance:
    :param created:
    :param kwargs:
    :return:
    """
    plan = instance.plan
    plan.users_num -= 1
    plan.save()





