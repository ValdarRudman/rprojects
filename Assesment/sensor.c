/**
 * \file
 *         Sensor for Assesment
 * \author
 *         Valdar Rudman <valdar.rudman@mycit.ie>
 */

#include "contiki.h"
#include "sht11-sensor.h"
#include "dev/light-sensor.h"
#include "net/rime/rime.h"
#include <stdio.h>

/*---------------------------------------------------------------------------*/

//Declare the process
PROCESS(send_sensor_info_process, "Sensor Node");

//Make the process start when the module is loaded
AUTOSTART_PROCESSES(&send_sensor_info_process);

/*---------------------------------------------------------------------------*/

//Variables for average tempature.
static int temp;
static int light;
static int count;

//Unicast connection
static struct unicast_conn uc;
//Broadcast connection
static struct broadcast_conn broadcast;

//Struct containg the readings from the sensors. This will be sent to the collector
struct values{
	
	uint16_t temp;
	uint16_t light;
};

/*---------------------------------------------------------------------------*/

//Get the light value
static int
get_light(void)
{
  return 10 * light_sensor.value(LIGHT_SENSOR_PHOTOSYNTHETIC) / 7;
}

//Get the tempature value
static int
get_temp(void)
{
  return ((sht11_sensor.value(SHT11_SENSOR_TEMP) / 10) - 396) / 10;
}

/*---------------------------------------------------------------------------*/

//If broadcast signal received executes the following code
static void
broadcast_recv(struct broadcast_conn *c, const linkaddr_t *from)
{
	
	struct values *msg;
	packetbuf_clear();
	msg = (struct values *) packetbuf_dataptr(); // int ar[1]; arr[0] =1;

	packetbuf_set_datalen(sizeof(struct values));

	msg->temp = (temp / count);
	msg->light = (light / count);
 
	//Test output of tempature and light for the sensor. Values are the average
	//printf("SENSOR VALUES -- temp: %d -- light: %d\n", msg->temp, msg->light);		

	//Reset values for temp, light and count to 0 when values have been sent/ This will be changed to implement an array of size x and we will store temp and light reading in there.
	//the value in the array will be shifted one to the left for new value and we can add the elements in the array and use the size of the array to get the average. This should be in a seperate method
	//Method called here? or just before etimer reset??
	temp = 0;
	light = 0;
	count = 0;
		
      	unicast_send(&uc, from);
	
}
//Calls broadcast_recv if a broadcast is recieved, executing whats in the broadcast_recv method
static const struct broadcast_callbacks broadcast_call = {broadcast_recv};

/*---------------------------------------------------------------------------*/

//Code that is executed if a unicast signal is received.
static void
recv_uc(struct unicast_conn *c, const linkaddr_t *from)
{

  //Code that you want to execute if unicast signal is received

}
//Execute recv_uc if unicast signal is received
static const struct unicast_callbacks unicast_callbacks = {recv_uc};

/*---------------------------------------------------------------------------*/

//Define the process code
PROCESS_THREAD(send_sensor_info_process, ev, data)
{
	
	// etimer, used to take tempature and light readings as set times
	static struct etimer etimer;

	//Variable to store tempature reading initially
	static int val;
	
	//On process exit, close both broadcast and unicast connections
	PROCESS_EXITHANDLER(broadcast_close(&broadcast);)
	PROCESS_EXITHANDLER(unicast_close(&uc);)

	PROCESS_BEGIN();
	
	//Open connections
	broadcast_open(&broadcast, 110, &broadcast_call);
	unicast_open(&uc, 111, &unicast_callbacks);

	while(1){
		
		//Set time for etimer
		etimer_set(&etimer, 1000);
		
		//Wait till timer up to take tempature and light readings
		PROCESS_WAIT_UNTIL(etimer_expired(&etimer));

		//Activate the sensors
		SENSORS_ACTIVATE(light_sensor);
		SENSORS_ACTIVATE(sht11_sensor);

		//Getting tempature value
		val = get_temp();		

		//Save tempature readings. Adds them up so later we can divide by counter to get average
		temp = temp + val;
		
		//Getting light value
		val = get_light();		
			
		//Save light readings. Adds them up so later we can divide by counter to get average
		light = light + val;

		//Count to count how many readings
		count = count + 1;
		
		//Reset etimer
		etimer_reset(&etimer);

		//Deactivate sensors
		SENSORS_DEACTIVATE(light_sensor);
		SENSORS_DEACTIVATE(sht11_sensor);

	}

	PROCESS_END();
}

/*---------------------------------------------------------------------------*/
