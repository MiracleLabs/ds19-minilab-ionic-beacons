import { Injectable } from '@angular/core';
import { Events, Platform } from 'ionic-angular';
//import { IBeacon } from 'ionic-native';
import { IBeacon } from '@ionic-native/ibeacon';

/*
  Generated class for the BeaconProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class BeaconProvider {
  delegate: any;
  region: any;
  constructor(public platform: Platform, public events: Events, private ibeacon: IBeacon) {
    console.log('Hello BeaconProvider Provider');
  }

  initialise(): any {
    let promise = new Promise((resolve, reject) => {



    // Request permission to use location on iOS
    this.ibeacon.requestAlwaysAuthorization();

    // create a new delegate and register it with the native layer
    this.delegate = this.ibeacon.Delegate();

    // Subscribe to some of the delegate’s event handlers
    this.delegate.didRangeBeaconsInRegion()
    .subscribe(
    data => {
    this.events.publish('didRangeBeaconsInRegion', data);
    },
    error => console.error()
    );

    // setup a beacon region – CHANGE THIS TO YOUR OWN UUID
    this.region = this.ibeacon.BeaconRegion('estimote','b9407f30-f5f8-466e-aff9-25556b57fe6d');

    // start ranging
    this.ibeacon.startRangingBeaconsInRegion(this.region)
    .then(
    () => {
    resolve(true);
    },
    error => {
    console.error('Failed to begin monitoring: ', error);
    resolve(false);
    }
    );

    });

    return promise;
    }
}
