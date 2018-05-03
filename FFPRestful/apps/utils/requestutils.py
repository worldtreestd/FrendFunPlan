# _*_ coding:utf-8 _*_
import requests, json
from rest_framework import serializers
from django.contrib.auth import get_user_model

User = get_user_model()

__author__ = 'Legend'
__date__ = '2017/12/31 7:56'


def validate_openid(access_token, open_id):
    from FFPRestful.settings import SOCIAL_AUTH_QQ_KEY
    response = requests.get(
        'https://graph.qq.com/user/get_user_info', params={
            'access_token': access_token,
            'oauth_consumer_key': SOCIAL_AUTH_QQ_KEY,
            'openid': open_id,
        }
    )
    redict = json.loads(response.text)
    status = redict['ret']
    if status != 0:

        raise serializers.ValidationError('')
    else:
        from random import Random
        # 如果该用户名已存在， 则往后面添加一个随机数
        if User.objects.filter(username=redict['nickname']).count():

            rand_count = Random()
            redict['nickname'] = redict['nickname'] + rand_count.randint(0, 10000)
            raise serializers.ValidationError('该用户已经存在')
    return redict
