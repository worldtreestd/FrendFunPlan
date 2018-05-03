# -*- coding: utf-8 -*-
# Generated by Django 1.11.3 on 2017-12-30 10:39
from __future__ import unicode_literals

import datetime
from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Circle',
            fields=[
                ('name', models.CharField(default='', help_text='圈子名称', max_length=20, verbose_name='圈子名称')),
                ('id', models.AutoField(help_text='圈子id', primary_key=True, serialize=False, verbose_name='圈子id')),
                ('address', models.CharField(default='', help_text='圈子地点', max_length=50, verbose_name='圈子地点')),
                ('desc', models.CharField(default='', help_text='圈子简介', max_length=200, verbose_name='圈子简介')),
                ('image', models.ImageField(blank=True, null=True, upload_to='circleimage/', verbose_name='图片')),
                ('created', models.CharField(blank=True, help_text='创建人', max_length=20, null=True, verbose_name='创建人')),
                ('add_time', models.DateTimeField(default=datetime.datetime.now, help_text='添加时间', verbose_name='添加时间')),
            ],
            options={
                'verbose_name': '圈子名',
                'verbose_name_plural': '圈子名',
            },
        ),
        migrations.CreateModel(
            name='CirclePartnerMessage',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('message', models.CharField(default='', help_text='聊天信息', max_length=300, verbose_name='聊天消息')),
                ('add_time', models.DateTimeField(default=datetime.datetime.now, verbose_name='添加时间')),
                ('circle', models.ForeignKey(help_text='所属圈子', on_delete=django.db.models.deletion.CASCADE, to='circles.Circle', verbose_name='所属圈子')),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL, verbose_name='圈内用户')),
            ],
            options={
                'verbose_name': '圈友会话',
                'verbose_name_plural': '圈友会话',
            },
        ),
    ]