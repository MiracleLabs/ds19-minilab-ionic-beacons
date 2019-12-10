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
