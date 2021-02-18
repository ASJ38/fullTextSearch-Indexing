# Volltextsuche und Indexierung von unstrukturierten Daten mit Elasticsearch und MongoDB


## Prolemstellung
Die Datenschutz-Grundverordnung (DSGVO), die am 25.05.2018 in Kraft getreten
ist, sieht eine Reorganisation der Prozesse in den Unternehmen im Hinblick auf
die Rechenschaftspflicht, die Transparenz, die Sicherheit und vieles mehr vor. Die
DSGVO enthält eine Reihe von Gesetzen mit dem Ziel, den Datenschutz von
natürlichen Personen innerhalb der EU zu gewährleisten (Artikel 1 Nummer 1)
Eine dieser Pflichten ist die Auskunftspflicht.
Genau das sollte auch bei großen Mengen unstrukturierter Daten unterstützt werden.

## Zielsetzung
Das Ziel war es, einen MapReduce-Job zu entwickeln, der den Inhalt von
Dateien aus dem Hadoop Distributed File System (HDFS) in Elasticsearch
indexiert und eine Java-Anwendung zu schreiben, die mithilfe der Abfragesprache
von Elasticsearch nach personenbezogenen Daten sucht und die gefilterten Daten
und Metadaten im JSON-Format in der NoSQL-Datenbank MongoDB speichert.


## Ausführung
Der verantwortliche Benutzer soll durch Eingabe eines Namens oder
einer eindeutigen Identifikationsnummer alle PDF-Dateien gezeigt bekommen, die den
Suchbegriff enthalten.Die Indizierung aus dem HDFS in Elasticsearch soll in Zukunft automatisiert werden.
Zum Zeitpunkt der Entwicklung musste dies noch manuell gestartet werden.
Ein User-Guide ist in diesem Repository erhalten.  
