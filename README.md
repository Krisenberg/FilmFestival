# Science-Fiction Film Festival app

## Configure Android Studio emulator next to the Windows Hyper-V
If you want to run the emulator, you should (or have to?) disable the Hyper-V using command:  
`bcdedit /set hypervisorlaunchtype off`  
In case you want to enable back the Hyper-V:  
`bcdedit /set hypervisorlaunchtype auto`    
> [!IMPORTANT]
> Restart to take effect and apply changes.

To check whether emulator driver is operating:
1. Navigate to:
	cd AppData\Local\Android\Sdk\emulator
2. Type:
	sc query gvm
3. Expect the result:
	SERVICE_NAME: gvm
       		...
       		STATE              : 4  RUNNING
	        ...