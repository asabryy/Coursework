#!/usr/bin/env python3

import sys
from datetime import datetime
import datetime as dt

def readfile(filename, lines):
    f = open(filename)
    for l in f:
        lines.append(l)
    f.close()

def add_rules(events, tempdict, keys):
    if tempdict["rule"] != None:
        i = tempdict["start"] + dt.timedelta(days = 7)
        j = tempdict["end"] + dt.timedelta(days = 7)
        ruleDate = datetime.strptime(tempdict["rule"].split("UNTIL=")[1].split(';')[0], "%Y%m%dT%H%M%S")
        while i < ruleDate:
            tempdictr = dict.fromkeys(keys)
            tempdictr["location"] = tempdict["location"]
            tempdictr["summary"] = tempdict["summary"]
            tempdictr["start"] = i
            tempdictr["end"] = j
            events.append(tempdictr)
            i = i + dt.timedelta(days = 7)
            j = j + dt.timedelta(days = 7)

def scan_events(lines, events):
    newEvent = 0
    keys = ["start", "end", "summary", "location", "rule"]

    for l in lines:
        if "BEGIN:VEVENT" in l:
            newEvent = 1
            tempdict = dict.fromkeys(keys)
        if "DTSTART" in l: 
            tempdict["start"] = datetime.strptime(l.split("DTSTART:")[1].strip(), "%Y%m%dT%H%M%S")
        if "DTEND" in l:
            tempdict["end"] = datetime.strptime(l.split("DTEND:")[1].strip(), "%Y%m%dT%H%M%S")
        if "LOCATION" in l:
            tempdict["location"] = l.split("LOCATION:")[1].strip()
        if "SUMMARY" in l:
            tempdict["summary"] = l.split("SUMMARY:")[1].strip()
        if "RRULE" in l:
            tempdict["rule"] = l.split("RRULE:")[1].strip()
        if "END:VEVENT" in l:
            events.append(tempdict)
            add_rules(events, tempdict, keys)
            tempdict = dict.fromkeys(keys)

def print_events(events, startdate, enddate):
    foundday = None
    daynumber = 0
    for e in events:
        if (e["start"].date() >= startdate.date()) and (e["start"].date() <= enddate.date()):
            if foundday != e["start"].date():
                foundday = e["start"].date()
                header = e["start"].strftime("%B %d, %Y (%a)")
                if daynumber > 0:
                    print("")
                print(header)
                print('-' * len(header))
                daynumber += 1
            if foundday == e["start"].date():
                print(e["start"].strftime("%l:%M %p"),"to",e["end"].strftime("%l:%M %p:"),e["summary"],"{{"+e["location"]+"}}")

def main():
    lines = []
    events = []

    if len(sys.argv) < 4:
        print("usage:",sys.argv[0]," --start=yyyy/mm/dd --end=yyyy/mm/dd --file=icsfile\n")
    else:
        startdate = datetime.strptime(sys.argv[1].split("--start=")[1], "%Y/%m/%d")
        enddate = datetime.strptime(sys.argv[2].split("--end=")[1], "%Y/%m/%d")
        filename = sys.argv[3].split("--file=")[1]

    readfile(filename, lines)
    scan_events(lines, events)
    events = sorted(events, key=lambda d: d["start"])
    print_events(events, startdate, enddate)

if __name__ == "__main__":
    main()
