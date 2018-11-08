/**
 * \file
 *         Collector for assesment project
 * \author
 *         6Valdar Rudman <valdar.rudman@mycit.ie>
 */

#include "contiki.h"
#include "net/rime/rime.h"
#include <stdio.h>

/*---------------------------------------------------------------------------*/

//Declare the process
PROCESS(broadcast_process, "Broadcast");
PROCESS(all_values_process, "All Values");

//Make the process start when the module is loaded
AUTOSTART_PROCESSES(&broadcast_process, &all_values_process);

/*---------------------------------------------------------------------------*/

#define MAX_SENSORS 10
#define MAX_NUMBER_OF_VALUES 6

//Connections
static struct broadcast_conn broadcast;
static struct unicast_conn uc;

//Struct of the values from the sensors
struct values{
	
	uint16_t temp;
	uint16_t light;
};

//Struct of the information we want from the sensor
struct sensor{
	
	//next sensor in list
	struct sensor *next;
	//array of values
	struct values all_values[MAX_NUMBER_OF_VALUES];
	//RIME value
	linkaddr_t addr;
	//Count of where we are in the array
	int count;

};

//Allocate memory for sensors
MEMB(sensors_memb, struct sensor, MAX_SENSORS);

//List of the sensors
LIST(sensors_list);

/*---------------------------------------------------------------------------*/

//If broadcast signal received executes the following code
static void
broadcast_recv(struct broadcast_conn *c, const linkaddr_t *from)
{
	
	//Code to execute if  broadcast signal received
	
}
//Calls broadcast_recv if a broadcast is recieved, executing whats in the broadcast_recv method
static const struct broadcast_callbacks broadcast_call = {broadcast_recv};

/*---------------------------------------------------------------------------*/

//Code that is executed if a unicast signal is received.
recv_uc(struct unicast_conn *c, const linkaddr_t *from)
{
	
	//Stuct for the sensor we will add to the list
	struct sensor *s;
	//Struct of the struct sent to us from the sensor
 	struct values *msg;
	//Assigning the data of the information received
	msg = packetbuf_dataptr();

	//Loop through our list to check if sensor is in it
	for(s = list_head(sensors_list); s != NULL; s = list_item_next(s)) {

		//If sensor is in break from loop
    		if(linkaddr_cmp(&s->addr, from)) {
      			break;
		}
  	}

	//If s is null, allocate memory and assign the sensor RIME value
	if(s == NULL) {

    		s = memb_alloc(&sensors_memb);

    		//if list is full just drops sensor
    		if(s == NULL) {
      			return;
    		}

		//Assiging RIME value
		linkaddr_copy(&s->addr, from);
		
		//Adding to list
		list_add(sensors_list, s);

	}

	//if count is 5, end of all_values array sets count to 0. This will overide any previous values in the struct values
	if(s-> count == MAX_NUMBER_OF_VALUES - 1){

		s->count = 0;
	}
	
	//Assign values
	s->all_values[s->count].temp = msg->temp;
	s->all_values[s->count].light = msg->light;

	//Test output of data from sensor that has sent the data
   	//printf("Unicast packet came from %d.%d with the temp sensed - %u and light sensed - %u\n", from->u8[0], from->u8[1], s->all_values[s->count].temp, s->all_values[s->count].light);
	
	//Increment count by 1
	s->count = s->count + 1;

}
//Execute recv_uc if unicast signal is received
static const struct unicast_callbacks unicast_callbacks = {recv_uc};

/*---------------------------------------------------------------------------*/

PROCESS_THREAD(broadcast_process, ev, data)
{
	
	// etimer, used to take tempature and light readings as set times
	static struct etimer etimer;

	//On process exit, close both broadcast and unicast connections
 	PROCESS_EXITHANDLER(broadcast_close(&broadcast);)
	PROCESS_EXITHANDLER(unicast_close(&uc);)

 	PROCESS_BEGIN();
	
	//Open connections
 	broadcast_open(&broadcast, 110, &broadcast_call);	
	unicast_open(&uc, 111, &unicast_callbacks);

  	while(1) {

    		//Set time for etimer
		etimer_set(&etimer, 2200);
		
		//Wait till timer to get readings from sensors
    		//PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&etimer));

		PROCESS_WAIT_EVENT();

		//Packet that will be sent to sensors
	    	packetbuf_copyfrom("Information", 12);

		//Broadcast packet to all sensors
	    	broadcast_send(&broadcast);
		
		//Print message that packet has been sent
	    	printf("broadcast message sent\n\n");
		
		//Reste etimer
		etimer_reset(&etimer);
		
 	}

  	PROCESS_END();
}
/*---------------------------------------------------------------------------*/

//Second process to print out the average of each sensor values
PROCESS_THREAD(all_values_process, ev, data)
{

	static struct etimer etimer;
		
	PROCESS_BEGIN();

	while(1){

		etimer_set(&etimer, 6600);
			
		PROCESS_WAIT_EVENT();
			
		//If the list contains all the senors will printout. Remove this if you want to print regardless how many sensors there are.
	//	if(list_length(sensors_list) == 10){

			struct sensor *s;
		
			//Go through list of sensors
			for(s = list_head(sensors_list); s != NULL; s = s->next){

				int i;
				//Temp and Light values that will be used to print out average
				int temp = 0;
				int light = 0;
			
				//Go through the all_values array
				for(i = 0; i < s->count; i++){
			
					//Adding the tempature and light values
					temp = temp + s->all_values[i].temp;
					light = light + s->all_values[i].light;
					
				}

				//Print out the sensor RIME Value and its values. Average temp/count
				printf("SENSOR %d.%d\nAverage Temp: %d\nAverage Light: %d\n\n", s->addr.u8[0], s->addr.u8[1], (temp / s->count), (light / s->count));
			}

	//	}

		etimer_reset(&etimer);

	}

	PROCESS_END();

}
/*---------------------------------------------------------------------------*/
