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
