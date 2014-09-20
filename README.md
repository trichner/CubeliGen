CubeliGen
=========

Nice wrapper to generate frames for the Velleman 5x5x5 LED cube.

After a night of reverse engineering and trial and error I figured out
what the format of *.cb5 files is.

Velleman K8018B
https://www.velleman.eu/products/view/?id=409306

##The *.cb5 File
This is the file contents of a *.cb5, containing one frame with only 1 LED is on. The following
was painfully reverse-engineered, not sure if correct...

### LED/planes encoding
For every plane 4 bytes are used, therefore the last 7 bits of every plane are used
for special flags. (4*8bit-5*5bit = 32bit-25bit = 7bit)

```
00 00 00 01 // 1. plane, the last bit flags this as a 'scene start' frame
00 00 00 00 // 2. plane, the last bits are used to encode the intensity, 0 corresponds to full intensity
00 00 00 00 // 3. plane
00 00 00 00 // 4. plane 
08 00 00 03 // 5. the 2nd last bit is the 'scene end' flag, the last one marks the absolute last frame
```

### Intensity encoding
The 7th byte is used for intensity encoding (the leftover 7bits from plane 2)
full -> 0x00
lvl4 -> 0x01
lvl3 -> 0x02
lvl2 -> 0x04
lvl1 -> 0x08
off  -> 0x10

Note that the first bit of this byte still encodes an LED.

### Special Flags
Flag | Description | Position (relative to frame start)
-----|-------------|----------
`scene_start` | flags the start of a scene | LSB of byte 4 (last bit of first plane)
`scene_end`   | flags the end of a scene | 2nd LSB of byte 20 (2nd last bit)
`final_frame` | flags the absolute last frame | LSB of byte 20 (last bit)





