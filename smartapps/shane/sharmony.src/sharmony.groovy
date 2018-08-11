/**
 *  SHarmony
 *
 *  Copyright 2018 Shane Lim
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "SHarmony",
    namespace: "shane",
    author: "Shane Lim",
    description: "control each device from harmony",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Title") {
		// TODO: put inputs here
        input name: "ip", type: "text", title: "IP", description: "Enter IP address", required: true
        input name: "port", type: "text", title: "Port", description: "Enter Port (default: 5222)", required: true
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    def host = ip + ":" + port
	discoverHarmonyHubs(host)
}

def discoverHarmonyHubs(host) {
	log.debug "hub dicoverying ${host}"
	//sendHubCommand(getHarmonyHubAction(host, "/hubs", "discoverResponse"))
}

def discoverResponse(resp) {
	def body = new groovy.json.JsonSlurper().parseText(parseLanMessage(resp.description).body)
    log.debug "discovered hugs: $body.hubs"
}

def getHarmonyHubAction(host, url, callback) {
	log.debug "getHubAction>> $host, $url, $callback"
	return new physicalgraph.device.HubAction("GET ${url} HTTP/1.1\r\nHOST: ${host}\r\n\r\n",
		physicalgraph.device.Protocol.LAN, "${host}", [callback: callback])
}



// TODO: implement event handlers