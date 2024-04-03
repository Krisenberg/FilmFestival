# Science-Fiction Film Festival app


## Configure Android Studio emulator next to the Windows Hyper-V
If you want to run the emulator, you should (or have to?) disable the Hyper-V using command:  
`bcdedit /set hypervisorlaunchtype off`  
In case you want to enable back the Hyper-V:  
`bcdedit /set hypervisorlaunchtype auto`    
> [!IMPORTANT]
> Restart to take effect and apply changes.


## Check whether a hypervisor is installed
1. Navigate to:  
	`cd AppData\Local\Android`  
2. Type:  
	`Sdk\emulator\emulator -accel-check`  
3. If the hypervisor is installed expect the result:  
	> accel:  
	> 0  
	> AEHD (version 2.0) is installed and usable.  
	> accel  


## Check whether emulator driver is operating
1. Navigate to:  
	`cd AppData\Local\Android\Sdk\emulator`  
2. If your version of AEHD (from the previous step) is 2.1 or higher type:  
	`sc query aehd`  
   Else type:  
	`sc query gvm`
3. Expect the result:  
	> SERVICE_NAME: gvm  
	> ...  
	> STATE			: 4  RUNNING  
	> ...


## App structure
Main folders of the application so far are:  
- `composables` - stores reusable composable parts that are logically extracted from the code (e.g. `BottomNavBar` which is used on many screens).
- `screens` - stores files resposible for each screen's layout. Eventually it will have as many files (screens) as designed in the prototypes.
- `ui/theme` - files related to the themes, colours etc.
- `utils` - extracted files that will be used by many others, they contain some useful utilities.

App will be written in the MVVM architectural pattern.

## To-Do's
[ ] Organize somehow themes and colours to standarize their usage  
[ ] Create every screen's scaffold and implement navigation graph  
[ ] Mock the data and recreate the application's prototype based on Figma  
[ ] Implement more logic (ViewModel)  
...