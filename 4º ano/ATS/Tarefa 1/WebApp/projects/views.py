import plotly
from django.shortcuts import render
from django.http import HttpResponse
from .utils.organize import organize_files
from .utils.sonar import *
from .utils.maven import run_maven
from django.shortcuts import redirect
from plotly.offline import download_plotlyjs, plot
import plotly.graph_objs as go
import plotly.express as px
import pandas as pd


def home(request):
    return render(request, 'home.html')


def run(request):
    source = request.POST['sourcePath']
    dest = request.POST['destPath']
    if source != "":
        organize_files(source, dest)
    n, failed = run_maven(dest, gen_login())
    return render(request, 'loadState.html', {'nrprojects': n, 'failed': failed})


def projects(request):
    metrics = get_metrics()
    metric_groups = get_metrics_group()
    project = get_projects_metrics(metrics)
    return render(request, 'projects.html', {'projects': project, 'metric_group': metric_groups})


def dashboard(request):
    overall = chart(get_metrics())
    top = overall['Technical Debt']['highest_file']
    btm = overall['Technical Debt']['lowest_file']
    top_btm = top_btm_projs(top, btm)
    domain = sorted(list(map(lambda x: x[0], filter(lambda y: y[0] != 'ncloc', top_btm[top]))))
    top_values = list(
        map(lambda x: x[1], sorted((filter(lambda y: y[0] != "ncloc", top_btm[top])), key=lambda x: x[0])))
    btm_values = list(
        map(lambda x: x[1], sorted((filter(lambda y: y[0] != "ncloc", top_btm[btm])), key=lambda x: x[0])))
    my_graph = graph_function(overall)
    context = {
        'overall': overall,
        'top_btm_proj': top_btm,
        'domain': domain,
        'top_val': top_values,
        'btm_val': btm_values,
        'plot': my_graph
    }
    return render(request, "dash.html", context)


def graph_function(overall):
    data = dict()
    for k, v in overall.items():
        if k != "Technical Debt":
            data[k] = v['avg']
    df = pd.DataFrame(data).melt(var_name="mtr")

    fig = px.box(df, y="value", facet_col="mtr", color="mtr"
                 , boxmode="overlay", points='all')

    return plotly.io.to_html(fig)


