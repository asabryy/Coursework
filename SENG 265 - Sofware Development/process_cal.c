#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

#define MAX_LINE_LEN 132
#define MAX_EVENTS 500

typedef struct
{
    bool exist;
    char *freq;
    char *until;
    struct tm until_d;
    time_t rDates[MAX_EVENTS];
    int rDates_size;
    char *btday;
} rrules;

typedef struct
{
    char *sdate_raw;
    char *edate_raw;
    struct tm start_d;
    struct tm end_d;
    rrules rules;
    char *location;
    char *summary;
} block_d;

void print_events(int from_yy, int from_mm, int from_dd,
                  int to_yy, int to_mm, int to_dd, block_d eventList[MAX_EVENTS], int events_size)
{
    struct tm temp_time, temp_to_time, temptemp_time, temp_event_time, *p_temp_time;
    int year = 0, month = 0, day = 0;
    time_t temp_diff, full_time;
    char buffer[100], buffer2[100], buffer3[100];
    char *tempdate;
    char temptime[MAX_EVENTS] = {0};

    memset(&temp_time, 0, sizeof(struct tm));
    memset(&temp_to_time, 0, sizeof(struct tm));
    memset(&temptemp_time, 0, sizeof(struct tm));

    temp_time.tm_year = from_yy - 1900;
    temp_time.tm_mon = from_mm - 1;
    temp_time.tm_mday = from_dd;

    temp_to_time.tm_year = to_yy - 1900;
    temp_to_time.tm_mon = to_mm - 1;
    temp_to_time.tm_mday = to_dd;

    time_t from_time = mktime(&temp_time);
    time_t to_time = mktime(&temp_to_time);

    while (difftime(from_time, to_time) <= 0)
    {
        int found_day = 0;
        strftime(buffer, 100, "%B %d, %Y (%a)", &temp_time);
        for (int i = 0; i < events_size - 1; i++)
        {

            tempdate = strtok(eventList[i].sdate_raw, "T");

            sscanf(tempdate, "%4d%2d%2d", &temptemp_time.tm_year, &temptemp_time.tm_mon, &temptemp_time.tm_mday);

            temptemp_time.tm_year -= 1900;
            temptemp_time.tm_mon -= 1;
            temp_diff = mktime(&temptemp_time);

            //strftime(buffer, 100, "%B %d, %Y (%a)", &temptemp_time);

            if (difftime(from_time, temp_diff) == 0 && found_day == 0)
            {
                strftime(buffer, 100, "%B %d, %Y (%a)", &temp_time);
                printf("%s\n", buffer);
                for(int k=0; k<strlen(buffer); k++){
                    printf("-");
                }
                printf("\n");
                found_day = 1;
            }

            if (difftime(from_time, temp_diff) == 0 && found_day == 1)
            {
                strftime(buffer2, 100, "%l:%M %p", &eventList[i].start_d);
                strcpy(temptime, buffer2);
                strftime(buffer3, 100, " to %l:%M %p: ", &eventList[i].end_d);
                strcat(temptime, buffer3);
                strcat(temptime, eventList[i].summary);
                strcat(temptime, " {{");
                if(eventList[i].location != NULL){
                    strcat(temptime, eventList[i].location);
                }
                strcat(temptime, "}}");
                printf("%s\n", temptime);
            }
            
            if(eventList[i].rules.exist == true){
                for(int j=0; j<eventList[i].rules.rDates_size; j++){
                    if(difftime(eventList[i].rules.rDates[j],from_time) == 0 && found_day == 0){
                        strftime(buffer, 100, "%B %d, %Y (%a)", &temp_time);
                        printf("%s\n", buffer);
                         for(int k=0; k<strlen(buffer); k++){
                            printf("-");
                        }
                        printf("\n");
                        found_day = 1;
                    }
                    
                    if(difftime(eventList[i].rules.rDates[j],from_time) == 0 && found_day == 1){
                        //printf("found rule repeat");
                        strftime(buffer2, 100, "%l:%M %p", &eventList[i].start_d);
                        strcpy(temptime, buffer2);
                        strftime(buffer3, 100, " to %l:%M %p: ", &eventList[i].end_d);          //fix this for sorting the event times.
                        strcat(temptime, buffer3);
                        strcat(temptime, eventList[i].summary);
                        strcat(temptime, " {{");
                        if(eventList[i].location != NULL){
                            strcat(temptime, eventList[i].location);
                        }
                        strcat(temptime, "}}");
                        printf("%s\n", temptime);
                    }
                }
            }


            if (found_day == 1 && i == events_size - 2)
            {
                printf("\n");
            }
        }

        temp_time.tm_mday += 1;
        from_time = mktime(&temp_time);
        p_temp_time = localtime(&from_time);
        temp_time = *p_temp_time;
    }
}

void readfile(char *filename, char line[MAX_EVENTS][MAX_LINE_LEN], int *size)
{
    FILE *f;
    char s[MAX_LINE_LEN];
    char dstart[MAX_LINE_LEN];
    int i = 0;

    f = fopen(filename, "r");
    if (!f)
    {
        return;
    }
    while (fgets(line[i], MAX_LINE_LEN, f))
    {
        line[i][strlen(line[i]) - 1] = '\0';
        i++;
    }

    *size = i;
    fclose(f);
    return;
}

void convert_time(char *string, struct tm *tm)
{
    int year = 0, month = 0, day = 0, hour = 0, min = 0, sec = 0;

    sscanf(string, "%4d%2d%2dT%2d%2d%2d", &year, &month, &day, &hour, &min, &sec);
    tm->tm_year = year - 1900;
    tm->tm_mon = month - 1;
    tm->tm_mday = day;
    tm->tm_hour = hour;
    tm->tm_min = min;
    tm->tm_sec = sec;
}

void format_rules(char *string, rrules *rules, struct tm tmStart)
{
    struct tm temp_time, *p_temp_time;
    time_t until_time, start_time;
    int dates_i = 0;

    temp_time.tm_year = tmStart.tm_year;
    temp_time.tm_mon = tmStart.tm_mon;
    temp_time.tm_mday = tmStart.tm_mday;

    start_time = mktime(&temp_time);

    char buffer[100];
    //rules->freq = string;
    char *until_raw;
    strtok(string, "=");
    rules->freq = strtok(NULL, ";");                //fix this for the "many.ics" freq rules
    strtok(NULL, "=");
    until_raw = strtok(NULL, ";");
    rules->until = until_raw;
    convert_time(until_raw, &rules->until_d);
    strtok(NULL, "=");
    rules->btday = strtok(NULL, ";");

    until_time = mktime(&rules->until_d);

    while(difftime(start_time, until_time) < 0 ){
        temp_time.tm_mday += 7;
        start_time = mktime(&temp_time);
        rules->rDates[dates_i] = start_time;
        //printf("added to array\n");
        dates_i++;
        p_temp_time = localtime(&start_time);
        temp_time = *p_temp_time;
    }

    rules->rDates_size = dates_i;


    
    //strftime(buffer, 100, "%B %d, %Y (%a)", &rules->until_d);
    //printf("%s\n", buffer);
}

void scan_events(char line[MAX_EVENTS][MAX_LINE_LEN], int size, block_d eventList[MAX_EVENTS], int *numberEvents)
{

    bool eEnd = false;
    bool cEnd = false;
    char *tempDate;
    int event_index = 0;
    char temp[100][MAX_LINE_LEN];
    int year = 0, month = 0, day = 0, hour = 0, min = 0, sec = 0;

    for (int i = 0; i < size; i++)
    {
        eEnd = false;
        block_d tempEvent;
        tempEvent.rules.exist = false;
        tempEvent.rules.btday = "";
        tempEvent.rules.freq = "";
        tempEvent.rules.until = "";

        if (strstr(line[i], "BEGIN:VEVENT"))
        {
            while (eEnd != true)
            {
                if (strstr(line[i], "DTSTART:"))
                {
                    strtok(line[i], ":");
                    tempDate = strtok(NULL, "\n");
                    tempEvent.sdate_raw = tempDate;
                    convert_time(tempDate, &tempEvent.start_d);
                }
                if (strstr(line[i], "DTEND:"))
                {
                    strtok(line[i], ":");
                    tempDate = strtok(NULL, "\n");
                    tempEvent.edate_raw = tempDate;
                    convert_time(tempDate, &tempEvent.end_d);
                }
                if (strstr(line[i], "LOCATION:"))
                {
                    strtok(line[i], ":");
                    tempEvent.location = strtok(NULL, "\n");
                }
                if (strstr(line[i], "SUMMARY:"))
                {
                    strtok(line[i], ":");
                    tempEvent.summary = strtok(NULL, "\n");
                }
                if (strstr(line[i], "RRULE:"))
                {
                    strtok(line[i], ":");
                    tempEvent.rules.exist = true;

                    //printf("found and added rule\n");

                    tempDate = strtok(NULL, "\n");
                    format_rules(tempDate, &tempEvent.rules, tempEvent.start_d);
                }
                i++;
                if (strstr(line[i], "END:VEVENT"))
                {
                    eEnd = true;
                }
            }
            eventList[event_index] = tempEvent;
            //printf("added event to list\n");
            event_index++;
        }
    }
    *numberEvents = event_index + 1;
}

int main(int argc, char *argv[])
{
    int from_y = 0, from_m = 0, from_d = 0;
    int to_y = 0, to_m = 0, to_d = 0;
    char *filename = NULL;
    char line[MAX_EVENTS][MAX_LINE_LEN];
    int i, size, numberEvents;
    block_d eventList[MAX_EVENTS];

    for (i = 0; i < argc; i++)
    {
        if (strncmp(argv[i], "--start=", 8) == 0)
        {
            sscanf(argv[i], "--start=%d/%d/%d", &from_y, &from_m, &from_d);
        }
        else if (strncmp(argv[i], "--end=", 6) == 0)
        {
            sscanf(argv[i], "--end=%d/%d/%d", &to_y, &to_m, &to_d);
        }
        else if (strncmp(argv[i], "--file=", 7) == 0)
        {
            filename = argv[i] + 7;
        }
    }

    if (from_y == 0 || to_y == 0 || filename == NULL)
    {
        fprintf(stderr,
                "usage: %s --start=yyyy/mm/dd --end=yyyy/mm/dd --file=icsfile\n",
                argv[0]);
        exit(1);
    }

    readfile(filename, line, &size);
    scan_events(line, size, eventList, &numberEvents);
    print_events(from_y, from_m, from_d, to_y, to_m, to_d, eventList, numberEvents);

    exit(0);
}
