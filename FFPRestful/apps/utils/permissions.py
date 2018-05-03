# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2017/12/22 20:37'

from rest_framework import permissions


class IsOwnerOrReadOnly(permissions.BasePermission):
    """"
    判断是否是当前用户
    """
    def has_object_permission(self, request, view, obj):
        if request.method in permissions.SAFE_METHODS:
            return True

        return obj.user == request.user
