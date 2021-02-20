from django.urls import path
from . import views

urlpatterns = [
    path('',  views.home, name="home"),
    path('run', views.run, name="run"),
    path('projects', views.projects, name="projects"),
    path('dashboard', views.dashboard, name="dashboard")
]

