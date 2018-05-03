# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2017/12/28 20:09'

import xadmin
from .models import Circle, CirclePartnerMessage, CircleHotSearch, CircleBanner


class CircleAdmin(object):
    list_display = ['id', 'name', 'user', 'image', 'desc', 'address', 'watch_num', 'add_time', ]
    search_fields = ['id', 'name']
    list_filter = ['add_time', 'user', 'address']


class CircleMessageAdmin(object):
    list_display = ['circle', 'user', 'message', 'add_time']
    search_fields = ['user', 'circle', 'add_time']
    list_filter = ['circle', 'user', 'add_time']


class CircleHotSearchAdmin(object):
    list_display = ['keyword', 'count', 'add_time']
    search_fields = ['keyword', 'count', 'add_time']
    list_filter = ['keyword', 'count', 'add_time']


class CircleBannerAdmin(object):
    list_display = ['circle', 'add_time', 'circle_image_url']
    search_fields = ['circle', 'add_time']
    list_filter = ['circle', 'add_time']

xadmin.site.register(CircleHotSearch, CircleHotSearchAdmin)
xadmin.site.register(CircleBanner, CircleBannerAdmin)
xadmin.site.register(Circle, CircleAdmin)
xadmin.site.register(CirclePartnerMessage, CircleMessageAdmin)
