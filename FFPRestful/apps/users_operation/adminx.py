# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2017/12/28 21:39'

from .models import ParticipatePlan, ParticipateCircle
import xadmin


class ParticipatePlanAdmin(object):
    list_display = ['user', 'plan', 'add_time']
    list_filter = ['user', 'add_time']


class ParticipateCircleAdmin(object):
    list_display = ['user', 'circle', 'add_time']
    list_filter = ['user', 'add_time']


xadmin.site.register(ParticipatePlan, ParticipatePlanAdmin)
xadmin.site.register(ParticipateCircle, ParticipateCircleAdmin)