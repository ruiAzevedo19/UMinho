from sonarqube import SonarQubeClient
import pprint
import itertools

url = 'http://localhost:9000'
username = "admin"
password = "admin"

sonar = SonarQubeClient(sonarqube_url=url, username=username, password=password)


def gen_login():
    sonar.user_tokens.revoke_user_token("projectsATS")
    return sonar.user_tokens.generate_user_token("projectsATS")['token']


def get_metrics():
    f = ["General", "Releasability", "SCM"]
    m = ",".join((list(
        map(lambda x: x['key'],
            filter(lambda x: x['domain'] not in f and x['key'] != 'ncloc_language_distribution',
                   sonar.metrics.search_metrics()))
    )
    ))
    return m


def get_metrics_group():
    f = ["General", "Releasability", "SCM"]
    m = ",".join(
        (
            sorted(
                list(set(map(lambda x: x['domain'],
                             filter(lambda x: x['domain'] not in f, sonar.metrics.search_metrics())))))
        )
    )
    return m


def get_projects_metrics(metrics):
    metric_domain = {}
    for s in sonar.metrics.search_metrics():
        metric_domain[s['key']] = (s['name'], s['domain'])
    projects = list(sonar.projects.search_projects())
    proj_measures = {}
    for p in projects:
        domain_measures = {}
        component = sonar.measures.get_component_with_specified_measures(component=p['key'], fields="metrics",
                                                                         metricKeys=metrics)
        for m in component.values():
            item = m['measures']
            for metric in item:
                if 'new' not in metric['metric'].split("_"):
                    key, domain = metric_domain[metric['metric']]
                    if not domain_measures.__contains__(domain):
                        domain_measures[domain] = [(key, float(metric['value']))]
                    else:
                        domain_measures[domain].append((key, float(metric['value'])))
                    proj_measures[p['key']] = domain_measures
    return proj_measures


def stats(filter_metric):
    overall = {}
    for metric in filter_metric:
        metric_attr = {
            'lowest_val': float("inf"),
            'lowest_file': "",
            'highest_val': float("-inf"),
            'highest_file': "",
            'avg': []
        }
        overall[metric] = metric_attr
    return overall


def filter_metrics():
    f = {
        'Maintainability': ['Technical Debt', 'Code Smells'],
        'Reliability': ['Bugs'],
        'Issues': ['Blocker Issues', 'Critical Issues', 'Issues'],
        'Size': ['Lines of Code', 'Classes'],
        'Security': ['Vulnerabilities'],
        'Complexity': ['Cyclomatic Complexity']
    }
    return f


def chart(metrics):
    projects = get_projects_metrics(metrics)
    n = len(get_projects_metrics(metrics))
    f = filter_metrics()
    filter_metric = list(itertools.chain.from_iterable([v for _, v in f.items()]))
    overall = stats(filter_metric)

    for proj_name, proj_content in projects.items():
        for domain, metrics in proj_content.items():
            if domain in f.keys():
                for name, value in metrics:
                    if name in filter_metric:
                        overall[name]['avg'].append(value)
                        if value < overall[name]['lowest_val']:
                            overall[name]['lowest_val'] = int(value)
                            overall[name]['lowest_file'] = proj_name
                        if value > overall[name]['highest_val']:
                            overall[name]['highest_val'] = int(value)
                            overall[name]['highest_file'] = proj_name
    for k, v in overall.items():
        if k == "Technical Debt":
            v['lowest_val'] = to_days(v['lowest_val'])
            v['highest_val'] = to_days(v['highest_val'])
    return overall


def to_days(minutes):
    h = minutes // 60
    days = h // 8
    hour = h % 8
    if days > 0:
        return f"{int(days)}d{int(hour)}h"


def top_btm_projs(top_proj, btm_proj):
    metrics = "code_smells,bugs,violations,ncloc,vulnerabilities"
    top = sonar.measures.get_component_with_specified_measures(component=top_proj, fields="metrics",
                                                               metricKeys=metrics)
    btm = sonar.measures.get_component_with_specified_measures(component=btm_proj, fields="metrics",
                                                               metricKeys=metrics)

    top_btm = {
        top_proj: [],
        btm_proj: []
    }
    for m in top['component']['measures']:
        top_btm[top_proj].append((m['metric'], int(m['value'])))

    for m in btm['component']['measures']:
        top_btm[btm_proj].append((m['metric'], int(m['value'])))

    return top_btm
