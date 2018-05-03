# _*_ coding:utf-8 _*_
__author__ = 'Legend'
__date__ = '2017/12/31 16:40'

from django.contrib.auth import get_user_model
from django.db.models.signals import post_save
from django.dispatch import receiver
from rest_framework.authtoken.models import Token

User = get_user_model()


@receiver(post_save, sender=User)
def create_user(sender, instance=None, created=False,**kwargs):
    if created:
        password = instance.password
        instance.set_password(password)
        instance.save()