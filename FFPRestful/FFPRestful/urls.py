"""FFPlanDfs URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.views.static import serve
from FFPRestful.settings import MEDIA_ROOT
from rest_framework.documentation import include_docs_urls
from rest_framework.routers import DefaultRouter
from rest_framework_jwt.views import obtain_jwt_token
import xadmin

from circles.views import CircleViewSet, CirclePartnerMessageViewSet,\
    CircleHotSearchViewSet, CircleBannerViewSet
from plans.views import PlanViewSet
from users_operation.views import ParticipatePlanViewSet, ParticipateCircleViewSet
from users.views import UserViewSet

router = DefaultRouter()

# 圈子列表
router.register('circles', CircleViewSet, base_name='circles'),

# 轮播图列表
router.register('banners', CircleBannerViewSet, base_name='banners')

# 计划列表
router.register('plans', PlanViewSet, base_name='plans'),

# 参与计划
router.register('partplans', ParticipatePlanViewSet, base_name='partplans'),

# 加入圈子
router.register('partcircles', ParticipateCircleViewSet, base_name='partcircles')

# 消息列表
router.register('messages', CirclePartnerMessageViewSet, base_name='messages')

# 搜索关键字列表
router.register('keywords', CircleHotSearchViewSet, base_name='keywords')

# 用户
router.register('users', UserViewSet, base_name='users')


urlpatterns = [
    url(r'^xadmin/', xadmin.site.urls),

    # # 第三方登录url
    # url('', include('social_django.urls', namespace='social')),

    url(r'media/(?P<path>.*)$', serve, {'document_root': MEDIA_ROOT}),

    url(r'^', include(router.urls)),

    # jwt认证接口
    url(r'^login/', obtain_jwt_token),

    url(r'docs/', include_docs_urls(title='友趣计划'))
]
