# BytePattern
Simply Byte array parsing using text pattern.

Give pattern like "l;i:i:l:s:a[10]:f:i:d" for pattner of little endian sequence of a {Integer, Integer, Long, Short, byte array of length 10, Float, Integer, Double}.
And given data (object array) to get byte array or vice versa.
## Pattern
syntax: {byte order};{sequence}
- Byte order:
  - b/B - Big endian
  - l/L - Little endian
- Sequence: {x}:{x}:{x}:{x}:{x}:......:{x}:{x}
  - b/B - Byte
  - s/S - short
  - i/I - integer
  - l/L - long
  - f/F - float
  - d/D - double
  - a[length] - byte array of length
 
