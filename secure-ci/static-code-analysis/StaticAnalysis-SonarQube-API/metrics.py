from sonarqube import SonarQubeClient
url = 'http://localhost:9000'
username = "student"
password = "student"
sonar = SonarQubeClient(sonarqube_url=url, username=username, password=password)

component = sonar.components.get_project_component_and_ancestors("StaticAnalysis-Target")

metrics = list(sonar.metrics.search_metrics())
#print(metrics)

component = sonar.measures.get_component_with_specified_measures(component="StaticAnalysis-Target",
                                                                 metricKeys="complexity")
print(component)