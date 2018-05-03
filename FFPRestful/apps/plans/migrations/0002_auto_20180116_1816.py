# -*- coding: utf-8 -*-
# Generated by Django 1.11.3 on 2018-01-16 18:16
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('plans', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='plan',
            name='address',
        ),
        migrations.AddField(
            model_name='plan',
            name='is_finished',
            field=models.BooleanField(default=False, verbose_name='是否完成'),
        ),
        migrations.AlterField(
            model_name='plan',
            name='user',
            field=models.ForeignKey(help_text='发布人', on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL, verbose_name='发布人'),
        ),
    ]