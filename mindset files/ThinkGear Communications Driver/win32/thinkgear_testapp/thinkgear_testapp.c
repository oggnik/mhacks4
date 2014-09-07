#include <stdlib.h>
#include <stdio.h>
#include <time.h>
//#include<windows.h>

#include "thinkgear.h"

#define FORMAT "%d\n"
/**
 * Prompts and waits for the user to press ENTER.
 */
void
wait() {
	FILE* state=fopen("state.txt","r");
	char c='0';
	fscanf(state,"%c",&c);
	while(c!='1')
	{
		fclose(state);
    //printf( "\n" );
    //printf( "Press the ENTER key...\n" );
    //fflush( stdout );
    //getc( stdin );
		//printf("GAHHHHH\n");
		//printf("%c\n",c);
		//fflush(stdout);
		state=fopen("state.txt","r");
		fscanf(state,"%c",&c);
	}
	fclose(state);
}

void
wait2() {
	 printf( "\n" );
    printf( "Press the ENTER key...\n" );
    fflush( stdout );
    getc( stdin );
}

/**
 * Program which prints ThinkGear Raw Wave Values to stdout.
 */
int
main( void ) {
    
    char *comPortName  = NULL;
    int   dllVersion   = 0;
    int   connectionId = 0;
    int   packetsRead  = 0;
    int   errCode      = 0;
    
    double secondsToRun = 0;
    time_t startTime    = 0;
    time_t currTime     = 0;
    char  *currTimeStr  = NULL;

	FILE* attention;
	FILE* bufferState=fopen("state.txt","w+");
	FILE* raw;
	FILE* alpha1;
	FILE* alpha2;
	FILE* beta1;
	FILE* beta2;
	FILE* delta;
	FILE* theta;
	FILE* gamma1;
	FILE* gamma2;
    
    /* Print driver version number */
    dllVersion = TG_GetDriverVersion();
    printf( "ThinkGear DLL version: %d\n", dllVersion );
	fwrite("1",sizeof(char),1,bufferState);
	fclose(bufferState);
    /* Get a connection ID handle to ThinkGear */
    connectionId = TG_GetNewConnectionId();
    if( connectionId < 0 ) {
        fprintf( stderr, "ERROR: TG_GetNewConnectionId() returned %d.\n",
                connectionId );
        wait2();
        exit( EXIT_FAILURE );
    }
    
    /* Set/open stream (raw bytes) log file for connection */
    errCode = TG_SetStreamLog( connectionId, "streamLog.txt" );
    if( errCode < 0 ) {
        fprintf( stderr, "ERROR: TG_SetStreamLog() returned %d.\n", errCode );
       wait2();
        exit( EXIT_FAILURE );
    }
    
    /* Set/open data (ThinkGear values) log file for connection */
    errCode = TG_SetDataLog( connectionId, "dataLog.txt" );
    if( errCode < 0 ) {
        fprintf( stderr, "ERROR: TG_SetDataLog() returned %d.\n", errCode );
        wait2();
        exit( EXIT_FAILURE );
    }
    
    /* Attempt to connect the connection ID handle to serial port "COM5" */
    /* NOTE: On Windows, COM10 and higher must be preceded by \\.\, as in
     *       "\\\\.\\COM12" (must escape backslashes in strings).  COM9
     *       and lower do not require the \\.\, but are allowed to include
     *       them.  On Mac OS X, COM ports are named like
     *       "/dev/tty.MindSet-DevB-1".
     */
    comPortName = "\\\\.\\COM3";
    errCode = TG_Connect( connectionId,
                         comPortName,
                         TG_BAUD_57600,
                         TG_STREAM_PACKETS );
    if( errCode < 0 ) {
        fprintf( stderr, "ERROR: TG_Connect() returned %d.\n", errCode );
        wait2();
        exit( EXIT_FAILURE );
    }
   

    /* Keep reading ThinkGear Packets from the connection for 5 seconds... */
    secondsToRun = 5;
    startTime = time( NULL );
	while(1){
		int i=0;
		attention=fopen("attention.txt","w+");
		raw=fopen("raw.txt","w+");
        alpha1=fopen("alpha1.txt","w+");
		alpha2=fopen("alpha2.txt","w+");
		beta1=fopen("beta1.txt","w+");
		beta2=fopen("beta2.txt","w+");
		delta=fopen("delta.txt","w+");
		theta=fopen("theta.txt","w+");
		gamma1=fopen("gamma1.txt","w+");
		gamma2=fopen("gamma2.txt","w+");
		
    while(i<50){// difftime(time(NULL), startTime) < secondsToRun ) {
		
        /* Read all currently available Packets, one at a time... */ 
        do {
            //printf("%d\n",i);
            /* Read a single Packet from the connection */
            packetsRead = TG_ReadPackets( connectionId, 1 );
            
            /* If TG_ReadPackets() was able to read a Packet of data... */
            if( packetsRead == 1 ) {
                
                /* If the Packet containted a new raw wave value... */
                if( TG_GetValueStatus(connectionId, TG_DATA_RAW) != 0 ) {
                    
                    /* Get the current time as a string */
                    currTime = time( NULL );
        			currTimeStr = ctime( &currTime );
					//int check;
					//check= (int)TG_GetValue(connectionId,TG_DATA_POOR_SIGNAL);
					if(TG_GetValue(connectionId,TG_DATA_POOR_SIGNAL)> 90)
					{
						printf("%d\n",(int)TG_GetValue(connectionId,TG_DATA_POOR_SIGNAL));
						fprintf(raw,FORMAT,0);
						fprintf(alpha1,FORMAT,0);
						fprintf(alpha2,FORMAT,0);
						fprintf(beta1,FORMAT,0);
						fprintf(beta2,FORMAT,0);
						fprintf(delta,FORMAT,0);
						fprintf(gamma1,FORMAT,0);
						fprintf(gamma2,FORMAT,0);
						fprintf(theta,FORMAT,0);
						fprintf(attention,FORMAT,0);
					}
					else
					{
						fprintf(raw,FORMAT,TG_GetValue(connectionId,TG_DATA_RAW));
						fprintf(alpha1,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_ALPHA1));
						fprintf(alpha2,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_ALPHA2));
						fprintf(beta1,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_BETA1));
						fprintf(beta2,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_BETA2));
						fprintf(delta,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_DELTA));
						fprintf(gamma1,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_GAMMA1));
						fprintf(gamma2,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_GAMMA2));
						fprintf(theta,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_THETA));
						fprintf(attention,FORMAT,(int)TG_GetValue(connectionId,TG_DATA_ATTENTION));
					}
					//printf(stdout,FORMAT,TG_GetValue(connectionId,TG_DATA_THETA));
                    /* Get and print out the new raw value */
                    //fprintf( stdout, "%s: raw: %d\n", currTimeStr,
                          //  (int)TG_GetValue(connectionId, TG_DATA_RAW) );
                    //fflush( stdout );
                      i++;
                } /* end "If Packet contained a raw wave value..." */
                
            } /* end "If TG_ReadPackets() was able to read a Packet..." */
          
        } while( packetsRead > 0 ); /* Keep looping until all Packets read */
    } /* end "Keep reading ThinkGear Packets for 5 seconds..." */
	fclose(raw);
	fclose(alpha1);
	fclose(alpha2);
	fclose(beta1);
	fclose(beta2);
	fclose(delta);
	fclose(theta);
	fclose(gamma1);
	fclose(gamma2);
	fclose(attention);
	bufferState=fopen("state.txt","w");
	fwrite("0",sizeof(char),1,bufferState);
	fclose(bufferState);
	wait();
	//printf("check\n");
	//fflush(stdout);
	/*bufferState=fopen("state.txt","w+");
	fprintf(bufferState,"%c","0");
	fclose(bufferState);*/
	}
    /* Clean up */
    TG_FreeConnection( connectionId );
    
    /* End program */
    //wait();
    return( EXIT_SUCCESS );
}
