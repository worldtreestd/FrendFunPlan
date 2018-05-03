from django.apps import AppConfig


class CirclesConfig(AppConfig):
    name = 'circles'
    verbose_name = '圈子'

    def ready(self):
        from circles.signals import search_count
