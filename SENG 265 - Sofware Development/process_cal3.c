/*
 * process_cal3.c
 *
 * Starter file provided to students for Assignment #3, SENG 265,
 * Fall 2021.
 */

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <math.h>
#include "emalloc.h"
#include "ics.h"
#include "listy.h"

#define MAX_LINE_LEN 132

/**
*  convert_time
*--------------
*  converts string of date ics format "YYYYMMDD'T'HHMMSS" into tm type
*
*  string: ics formated string 
*  tm: tm structure that stores date and time values
*/
void convert_time(char *string, struct tm *tm) {
    int year = 0, month = 0, day = 0, hour = 0, min = 0, sec = 0;

    sscanf(string, "%4d%2d%2dT%2d%2d%2d", &year, &month, &day, &hour, &min, &sec);
    tm->tm_year = year - 1900;
    tm->tm_mon = month - 1;
    tm->tm_mday = day;
    tm->tm_hour = hour;
    tm->tm_min = min;
    tm->tm_sec = sec;
}

/**
*  print_events
*--------------
*  Takes in list of events (block_d) that were scanned form ics and the user
*  inputed start and end date, then check for events that fall in between
*  inputed dates.
*
*  Uses add_to_day function to create list of events to be sorted later using
*  bubbleSort to print out events in sorted order of time, for each day.
*/
void print_events(int from_yy, int from_mm, int from_dd, int to_yy, int to_mm, int to_dd, node_t* eventList){
    struct tm from_tm, to_tm, temp_tm, temp_tm2, *p_temp_time;
    int year = 0, month = 0, day = 0;
    time_t start_time, event_time;
    char *tempdate;
    char buffer[100], buffer2[100], buffer3[100], bufferD[17];
    int number_of_days = 0;

    memset(&from_tm, 0, sizeof(struct tm));
    memset(&to_tm, 0, sizeof(struct tm));
    memset(&temp_tm, 0, sizeof(struct tm));

    from_tm.tm_year = from_yy - 1900;
    from_tm.tm_mon = from_mm - 1;
    from_tm.tm_mday = from_dd;

    to_tm.tm_year = to_yy - 1900;
    to_tm.tm_mon = to_mm - 1;
    to_tm.tm_mday = to_dd;

    time_t from_time = mktime(&from_tm);
    time_t to_time = mktime(&to_tm);


    while (difftime(from_time, to_time) <= 0){
        node_t* current = eventList;
        char* string;
        int found_day = 0;
        while(current != NULL){
            strcpy(bufferD, current->val->dtstart);
            tempdate = strtok(bufferD, "T");
            sscanf(tempdate, "%4d%2d%2d", &temp_tm.tm_year, 
                    &temp_tm.tm_mon, &temp_tm.tm_mday);
            temp_tm.tm_year -= 1900;
            temp_tm.tm_mon -= 1;
            event_time = mktime(&temp_tm);
    
            if((difftime(from_time, event_time) == 0) && found_day == 0){
                if(number_of_days > 0){
                    printf("\n");
                }
                strftime(buffer, 100, "%B %d, %Y (%a)", &temp_tm);
                printf("%s\n", buffer);
                for(int k=0; k<strlen(buffer); k++) {
                    printf("-");
                }
                printf("\n");
                number_of_days += 1;
                found_day = 1;
            }

            if(difftime(from_time, event_time) == 0 && found_day == 1){
                convert_time(current->val->dtstart, &temp_tm2);
                strftime(buffer2, 100, "%l:%M %p", &temp_tm2);
                string = buffer2;

                convert_time(current->val->dtend, &temp_tm2);
                strftime(buffer3, 100, " to %l:%M %p: ", &temp_tm2);
                strcat(string, buffer3);
                strcat(string, current->val->summary);
                strcat(string, " {{");
                if (current->val->location != NULL) {
                    strcat(string, current->val->location);
                }
                strcat(string, "}}");
                printf("%s\n", string);

            }

            current = current->next;
        }
        from_tm.tm_mday += 1;
        from_time = mktime(&from_tm);
        p_temp_time = localtime(&from_time);
        from_tm = *p_temp_time;
    }

}

/**
*  scan_events
*-------------
*  Given array of lines that were retrived from the ics file an array of type
*  block_d struct that stores information of each event.
*
*  Scans each line to see if an event input starts with "BEGIN:VEVENT" and
*  and iterates to adding next event at "END:VEVENT"
*  Each paramater is added to the its labeled variable in the block_d struct
*/    
void scan_events(char line[MAX_LINE_LEN], event_t* newEvent, bool* eEnd) {
    char *tempDate;
    //start of block_d event
    if (strstr(line, "DTSTART:")) {
        strtok(line, ":");
        strcpy(newEvent->dtstart, strtok(NULL, "\n"));
    }
    if (strstr(line, "DTEND:")) {
        strtok(line, ":");
        strcpy(newEvent->dtend, strtok(NULL, "\n"));
    }
    if (strstr(line, "LOCATION:")) {
        strtok(line, ":");
        tempDate = strtok(NULL, "\n");
        if(tempDate != NULL){
            strcpy(newEvent->location, tempDate);
        }
        
    }
    if (strstr(line, "SUMMARY:")) {
        strtok(line, ":");
        strcpy(newEvent->summary, strtok(NULL, "\n"));
    }
    if (strstr(line, "RRULE:")) {
        strtok(line, ":");
        strcpy(newEvent->rrule, strtok(NULL, "\n"));
    }
    if (strstr(line, "END:VEVENT")) {
        *eEnd = true;
    }
}

event_t* create_event(){
    event_t* newEvent = (event_t*)emalloc(sizeof(event_t));
    return newEvent;
}

/**
*  format_rules
*--------------
*  Takes in the string parsed form "RRULES=", and stores each paramater in
*  in a rrule type of each event. 
*
*  Uses "UNTIL" paramater and "FREQ" to create an array of dates where the
*  event would be expected to be repeated. Sotered in time_t format.
*/
node_t* format_rules(node_t* eventList, event_t* event_info) {
    struct tm until_tm, start_tm, end_tm, *p_temp_time, *p_temp_time2;
    time_t    until_time, start_time, end_time;
    char buffer2[100], buffer1[100];
    event_t *newEvent;

    printf("%s\n", event_info->dtstart);
    convert_time(event_info->dtstart, &start_tm);
    convert_time(event_info->dtend, &end_tm);

    char *buffer = strstr(event_info->rrule, "UNTIL=");
    strtok(buffer, "=");
    strcpy(event_info->rrule, strtok(NULL, ";"));
    
    convert_time(event_info->rrule, &until_tm);
    start_time = mktime(&start_tm);
    end_time = mktime(&end_tm);

    until_time = mktime(&until_tm);
    while(difftime(start_time, until_time) < 0){
        start_tm.tm_mday += 7;
        end_tm.tm_mday += 7;

        start_time = mktime(&start_tm);
        end_time = mktime(&end_tm);
        
        p_temp_time = localtime(&start_time);
        start_tm = *p_temp_time;

        p_temp_time2 = localtime(&end_time);
        end_tm = *p_temp_time2;
        
        //strftime(buffer1, 100, "%B %d, %Y (%a)", &start_tm);
        //printf("%s\n", buffer1);
        
        if(difftime(start_time, until_time) < 0){
            newEvent = create_event();

            strftime(newEvent->dtstart, 17, "%Y%m%dT%H%M%S", &start_tm);
            strftime(newEvent->dtend, 17, "%Y%m%dT%H%M%S", &end_tm);
            strcpy(newEvent->summary, event_info->summary);
            strcpy(newEvent->location, event_info->location);
            eventList = add_inorder(eventList, new_node(newEvent));
        }
    }
    return eventList;
}


/**
*  read_file
*-----------
*  Reads ics file line by line and stores lines in array
*
*  filename: filename retrived from arguments in command line line[][]: array to
*  store each line of ics file
*/
node_t* read_file(char *filename, node_t* eventList) {
    FILE *f;
    bool eEnd = false;
    char line[MAX_LINE_LEN];
    event_t *newEvent;


    f = fopen(filename, "r");
    if (!f) {
        return NULL;
    }
    while (fgets(line, MAX_LINE_LEN, f)) {
        eEnd = false;
        newEvent = create_event();
        if (strstr(line, "BEGIN:VEVENT")) {
            while(eEnd != true) {
                fgets(line, MAX_LINE_LEN, f);
                scan_events(line, newEvent, &eEnd);
            }
            eventList = add_inorder(eventList, new_node(newEvent));

            if(strcmp(newEvent->rrule, "") != 0){
                eventList = format_rules(eventList, newEvent);
            }
        }
    }

    fclose(f);
    free(newEvent);
    return eventList;
}


int main(int argc, char *argv[])
{
    int     from_y = 0, from_m = 0, from_d = 0;
    int     to_y = 0, to_m = 0, to_d = 0;
    char    *filename = NULL;
    //node_t    *line = NULL;
    int     i;
    node_t *eventList = NULL;

    for (i = 0; i < argc; i++) {
        if (strncmp(argv[i], "--start=", 8) == 0) {
            sscanf(argv[i], "--start=%d/%d/%d", &from_y, &from_m, &from_d);
        } else if (strncmp(argv[i], "--end=", 6) == 0) {
            sscanf(argv[i], "--end=%d/%d/%d", &to_y, &to_m, &to_d);
        } else if (strncmp(argv[i], "--file=", 7) == 0) {
            filename = argv[i] + 7;
        }
    }

    if (from_y == 0 || to_y == 0 || filename == NULL) {
        fprintf(stderr,
                "usage: %s --start=yyyy/mm/dd --end=yyyy/mm/dd --file=icsfile\n",
                argv[0]);
        exit(1);
    }

    eventList = read_file(filename, eventList);
    print_events(from_y, from_m, from_d, to_y, to_m, to_d, eventList);                                        
    
    node_t* temp;
    while(eventList != NULL) {
        temp = eventList;
        eventList = eventList -> next;
        free(temp->val);
        free(temp);
    }
    exit(0);

}
