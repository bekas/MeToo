from django.conf.urls import patterns, include, url

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    url(r'^$', 'metoo.mainServer.views.mainPage'), 
    url(r'^what/', 'metoo.mainServer.views.whatPage'), 
    url(r'^about/', 'metoo.mainServer.views.aboutPage'), 
    url(r'^download/', 'metoo.mainServer.views.downloadPage'), 
    url(r'^dev/', 'metoo.mainServer.views.devPage'), 
    
    # Examples:
    # url(r'^$', 'metoo.views.home', name='home'),
    # url(r'^metoo/', include('metoo.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # url(r'^admin/', include(admin.site.urls)),
)
