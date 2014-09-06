#include <stdlib.h>
#include <stdio.h>
#include <time.h>

#include "thinkgear.h"

#define FORMAT "%f\n"
/**
 * Prompts and waits for the user to press ENTER.
 */
void
wait() {
	FILE* state=fopen("state.txt","r");
	char c;
	fscanf(state,"%c",c);
	while(c!='1')
	{
		fclose(state);
    //printf( "\n" );
    //printf( "Press the ENTER key...\n" );
    //fflush( stdout );
    //getc( stdin );
		state=fopen("state.txt","r");
	}
	fclose(state);
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

	FILE* bufferState=fopen("state.txt","w");
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
    fwrite("0",sizeof(char),1,bufferState);
	fclose(bufferState);
    /* Get a connection ID handle to ThinkGear */
    connectionId = TG_GetNewConnectionId();
    if( connectionId < 0 ) {
        fprintf( stderr, "ERROR: TG_GetNewConnectionId() returned %d.\n",
                connectionId );
       // wait();
        exit( EXIT_FAILURE );
    }
    
    /* Set/open stream (raw bytes) log file for connection */
    errCode = TG_SetStreamLog( connectionId, "streamLog.txt" );
    if( errCode < 0 ) {
        fprintf( stderr, "ERROR: TG_SetStreamLog() returned %d.\n", errCode );
       // wait();
        exit( EXIT_FAILURE );
    }
    
    /* Set/open data (ThinkGear values) log file for connection */
    errCode = TG_SetDataLog( connectionId, "dataLog.txt" );
    if( errCode < 0 ) {
        fprintf( stderr, "ERROR: TG_SetDataLog() returned %d.\n", errCode );
        //wait();
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
        wait();
        exit( EXIT_FAILURE );
    }
   

    /* Keep reading ThinkGear Packets from the connection for 5 seconds... */
    secondsToRun = 5;
    startTime = time( NULL );
	while(1){
		int i=0;
		wait();
		raw=fopen("raw.txt","w+");
        alpha1=fopen("alpha1.txt","w+");
		alpha2=fopen("alpha2.txt","w+");
		beta1=fopen("beta1.txt","w+");
		beta2=fopen("beta2.txt","w+");
		delta=fopen("delta.txt","w+");
		theta=fopen("theta.txt","w+");
		gamma1=fopen("gamma1.txt","w+");
		gamma2=fopen("gamma2.txt","w+");
    while(i<500){// difftime(time(NULL), startTime) < secondsToRun ) {
		
        /* Read all currently available Packets, one at a time... */
        do {
            
            /* Read a single Packet from the connection */
            packetsRead = TG_ReadPackets( connectionId, 1 );
            
            /* If TG_ReadPackets() was able to read a Packet of data... */
            if( packetsRead == 1 ) {
                
                /* If the Packet containted a new raw wave value... */
                if( TG_GetValueStatus(connectionId, TG_DATA_RAW) != 0 ) {
                    
                    /* Get the current time as a string */
                    currTime = time( NULL );
        			currTimeStr = ctime( &currTime );
					fprintf(raw,FORMAT,TG_GetValue(connectionId,TG_DATA_RAW));
					fprintf(alpha1,FORMAT,TG_GetValue(connectionId,TG_DATA_ALPHA1));
					fprintf(alpha2,FORMAT,TG_GetValue(connectionId,TG_DATA_ALPHA2));
					fprintf(beta1,FORMAT,TG_GetValue(connectionId,TG_DATA_BETA1));
					fprintf(beta2,FORMAT,TG_GetValue(connectionId,TG_DATA_BETA2));
					fprintf(delta,FORMAT,TG_GetValue(connectionId,TG_DATA_DELTA));
					fprintf(gamma1,FORMAT,TG_GetValue(connectionId,TG_DATA_GAMMA1));
					fprintf(gamma2,FORMAT,TG_GetValue(connectionId,TG_DATA_GAMMA2));
					fprintf(theta,FORMAT,TG_GetValue(connectionId,TG_DATA_THETA));
                    /* Get and print out the new raw value */
                    fprintf( stdout, "%s: raw: %d\n", currTimeStr,
                            (int)TG_GetValue(connectionId, TG_DATA_RAW) );
                    fflush( stdout );
                    
                } /* end "If Packet contained a raw wave value..." */
                
            } /* end "If TG_ReadPackets() was able to read a Packet..." */
            i++;
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
	}
    /* Clean up */
    TG_FreeConnection( connectionId );
    
    /* End program */
    wait();
    return( EXIT_SUCCESS );
}
