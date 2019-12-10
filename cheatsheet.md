# DS19 Open Lab | Building an Ionic App that detects BLE Beacons(Estimote)

The below markdown file consists of commands and code snippets that will help you complete the lab - Building an Ionic App that detects BLE Beacons(Estimote).

## Commands

### Install Cordova 

```shell
npm install -g cordova
```
### Install Ionic Framework

```shell
npm install -g ionic
```

### Create an Ionic Application

```shell
ionic start <your-application-name> <template> --type=ionic-angular
```

### Create a page in Ionic Application

```shell
ionic g page <your-page-name>
```
### Create a provider in Ionic Application

```shell
ionic g service providers/<your-provider-name>
```
### Simulate the App in browser

```shell
ionic serve
```
### Simulate the App in browser along with platform

```shell
ionic serve -l
```

## Code Snippets

### app.module.ts

```javascript
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule, AlertController } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';

import { IBeacon } from '@ionic-native/ibeacon';
import { BeaconProvider } from '../providers/beacon/beacon';


@NgModule({
  declarations: [
    MyApp,

  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,

  ],
  providers: [
    StatusBar,
    SplashScreen,
    IBeacon,
    AlertController,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    BeaconProvider
  ]
})
export class AppModule {}


```

### home.html

```html
<!--
  Generated template for the HomePage page.

  See http://ionicframework.com/docs/components/#navigation for more info on
  Ionic pages and navigation.
-->


<ion-header>
  <ion-navbar color="primary">
  <ion-title text-center>
  My Beacons
  </ion-title>
  </ion-navbar>
  </ion-header>

  <ion-content>
    <div>
    <ion-header text-center style="color:#d33257;  font-size: 1.7em; ">Available Devices ({{listLength}}) </ion-header>
  </div>
  <br>
  <ion-list >
  <ion-item *ngFor='let beacon of beaconList' (click)="details(beacon)">
  <ion-grid>
  <ion-row>
  <ion-col>Major</ion-col>
  <ion-col>{{beacon.major}}</ion-col>
  </ion-row>
  <ion-row>
  <ion-col>Minor</ion-col>
  <ion-col>{{beacon.minor}}</ion-col>
  </ion-row>
  </ion-grid>
  <ion-icon name="md-arrow-dropright" item-end></ion-icon>
  </ion-item>

  </ion-list>
  </ion-content>

```

### home.ts

```javascript
import { Component, NgZone } from '@angular/core';
import { IonicPage, NavController, NavParams, Platform, Events } from 'ionic-angular';
import { BeaconProvider } from '../../providers/beacon/beacon';
import { BeaconModel } from '../../models/beacon-module';
import { AlertController } from 'ionic-angular';

/**
 * Generated class for the HomePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class HomePage {
  beacons: BeaconModel[] = [];
  beaconList:any
  zone: any;
  count=0;
  listLength: any;
  constructor(public navCtrl: NavController,private alertCtrl: AlertController, public platform: Platform, public beaconProvider: BeaconProvider, public events: Events) {
 // required for UI update
this.zone = new NgZone({ enableLongStackTrace: false });
}

ionViewDidLoad() {
this.platform.ready().then(() => {
this.beaconProvider.initialise().then((isInitialised) => {
if (isInitialised) {
this.listenToBeaconEvents();
}
});
});
}

listenToBeaconEvents() {
this.events.subscribe('didRangeBeaconsInRegion', (data) => {

// update the UI with the beacon list
this.zone.run(() => {

this.beacons = [];

this.beaconList = data.beacons;
this.listLength =this.beaconList.length;
console.log("beaconlist",this.beaconList)
this.beaconList.forEach((beacon) => {
let beaconObject = new BeaconModel(beacon);
this.beacons.push(beaconObject);
this.count  = this.count;
console.log("count", this.count);
});


});

});

console.log(this.beacons);
}
details(data){

this.presentConfirm(data);
console.log(data)
}



presentConfirm(data) {
  if(data.minor=="665"){
    let alert = this.alertCtrl.create({
      title: 'Estimote Beacon',
      message: 'You are now in Electroincs Isle',
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          handler: () => {
            console.log('Cancel clicked');
          }
        },
        {
          text: 'ok',
          handler: () => {
            this.navCtrl.push('DetailsPage',{"beacon":data})
          }
        }
      ]
    });
    alert.present();

    }
    else if(data.minor=="48309"){
      let alert = this.alertCtrl.create({
        title: 'Estimote Beacon',
        message: 'You are now in Sports Isle',
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel',
            handler: () => {
              console.log('Cancel clicked');
            }
          },
          {
            text: 'ok',
            handler: () => {
              this.navCtrl.push('DetailsPage',{"beacon":data})
            }
          }
        ]
      });
      alert.present();

    }

    else if(data.minor=="1"){
      let alert = this.alertCtrl.create({
        title: 'Estimote Beacon',
        message: 'You are now in fashion Isle',
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel',
            handler: () => {
              console.log('Cancel clicked');
            }
          },
          {
            text: 'ok',
            handler: () => {
              this.navCtrl.push('DetailsPage',{"beacon":data})
            }
          }
        ]
      });
      alert.present();

    }
    else if(data.minor=="31040"){
      let alert = this.alertCtrl.create({
        title: 'Estimote Beacon',
        message: 'You are now in Watches Isle',
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel',
            handler: () => {
              console.log('Cancel clicked');
            }
          },
          {
            text: 'ok',
            handler: () => {
              this.navCtrl.push('DetailsPage',{"beacon":data})
            }
          }
        ]
      });
      alert.present();

    }
    else{
      let alert = this.alertCtrl.create({
        title: 'Estimote Beacon',
        subTitle: 'Unauthorized',
        buttons: ['Dismiss']
      });
      alert.present();
    }

}
}

```

### details.html

```html
ion-header>
  <ion-navbar color="primary">
    <ion-title  >details</ion-title>
  </ion-navbar>
</ion-header>

<ion-content text-center >

    <div *ngIf="beacon1"   class="container1">
        <img class="beaconImg" src="assets/imgs/beacon.png" >
    <img class="itemImgWatch"src="assets/imgs/iphone.png" >
    <div text-center>
      <h1 class="textColor">Brand new Iphone X</h1>
      <br>
      <h1 class="price">RS 45000</h1>
        <button expand="full" round ion-button color="secondary" >SHOP NOW</button>
    </div>
    </div>

  <div *ngIf="beacon2"  class="container2">
    <img class="beaconImg" src="assets/imgs/beacon.png" >
<img class="itemImg"src="assets/imgs/shoe.png" >
<div text-center>
  <h1 class="desc">Mens Synthetic Leather shoes</h1>
  <br>
  <h1 class="price">RS 3000</h1>
    <button expand="full" round ion-button color="secondary" >SHOP NOW</button>
</div>
</div>

<div  *ngIf="beacon3" class="container3">
    <img class="beaconImg" src="assets/imgs/beacon.png" >
    <img class="itemImg"src="assets/imgs/shirt.png" >
<div text-center>
  <h1 class="desc">Mens Casual Shirt</h1>
  <br>
  <h1 class="price">RS 1000</h1>
    <button expand="full" round ion-button color="secondary" >SHOP NOW</button>
</div>
</div>

<div *ngIf="beacon4"  class="container4">
    <img class="beaconImg" src="assets/imgs/beacon.png" >
    <img class="itemImgWatch"src="assets/imgs/wrist.png" >
<div text-center>
  <h1 class="textColor">Branded watches</h1>
  <br>
  <h1 class="price">RS 5000</h1>
    <button expand="full" round ion-button color="secondary" >SHOP NOW</button>
</div>
</div>




<!-- <div *ngIf="beacon2" style="height:100%; background-color: #cac900;">
    <h5>{{beaconData.minor}}</h5>
    </div>

    <div *ngIf="beacon3" style="height:100%; background-color: #f0a3ce;">
        <h5>{{beaconData.minor}}</h5>
        </div>

        <div *ngIf="beacon4" style="height:100%; background-color: green;">
            <h5>{{beaconData.minor}}</h5>
            </div> -->
</ion-content>

```

### details.ts

```javascript
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';

/**
 * Generated class for the DetailsPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-details',
  templateUrl: 'details.html',
})
export class DetailsPage {
beaconData:any;
beacon1:boolean;
beacon2:boolean;
beacon3:boolean;
beacon4:boolean;
  beacon5: boolean;
  constructor(public navCtrl: NavController, public navParams: NavParams) {
   this.beaconData = this.navParams.get('beacon');
   console.log(this.beaconData);
   this.beacon1=false;
   this.beacon2=false;
   this.beacon3=false;
   this.beacon4=false;
   this.beacon5=false;
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad DetailsPage');
    if(this.beaconData.minor=="665"){
    this.beacon1=true;

    }
    else if(this.beaconData.minor=="48309"){
      this.beacon2=true;

    }

    else if(this.beaconData.minor=="1"){
      this.beacon3=true;

    }
    else if(this.beaconData.minor == "31040"){
      this.beacon4=true;
    }
    else{
      this.beacon5 = true;
    }

  }



}

```

### beacon.ts

```javascript
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

```

