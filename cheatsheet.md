# DS18 Mini Lab | Building an Android App that detects BLE Beacons(Estimote) 
 
The below file consists of commands, links and code snippets that will help you complete and understand the lab - Building an Android App that detects BLE Beacons(Estimote)  
  
## Code Snippets  
  
To register Beacon MAC address into Android application  

### [MainActivity.java](http://mainactivity.java/)

```
if(address.equals("E3:91:4E:EE:06:BC")){  
AlertDialog.Builder alertDialog4 = new AlertDialog.Builder(MainActivity.this);  
LayoutInflater layoutInflater  
= (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
alertDialog4.setTitle("Estimote Beacon");  
alertDialog4.setMessage("You are now in Mens Clothing Isle");  
alertDialog4.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
public void onClick(DialogInterface dialog, int which) {  
});
```



