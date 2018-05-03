# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2017/12/30 10:02'

from django.contrib.auth import get_user_model
from xadmin import views
import xadmin


class GlobalSetting(object):
    site_title = '友趣计划'
    site_footer = '友趣计划后台管理'


# class UserAdmin(object):
#     list_display = ['nickname', 'end_time', 'content', 'address', 'add_time']

xadmin.site.register(views.CommAdminView, GlobalSetting)
