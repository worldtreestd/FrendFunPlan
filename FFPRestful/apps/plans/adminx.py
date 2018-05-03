# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2017/12/28 21:39'
from .models import Plan
import xadmin


class PlanAdmin(object):
    list_display = ['from_circle', 'user', 'end_time', 'content', 'add_time']
    search_fields = ['from_circle', ]
    list_filter = ['add_time', 'user', ]

xadmin.site.register(Plan, PlanAdmin)