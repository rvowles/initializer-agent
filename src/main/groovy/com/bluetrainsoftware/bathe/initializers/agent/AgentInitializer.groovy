package com.bluetrainsoftware.bathe.initializers.agent

import bathe.BatheInitializer
import com.bluetrainsoftware.agent.AgentLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Attempts to to Agent loading if this is found defined in the system properties.
 *
 * @author Richard Vowles - https://plus.google.com/+RichardVowles
 */
class AgentInitializer implements BatheInitializer {
	public static final String AGENT_LOADING = "webapp.agents"; // agents to load if in dev mode
	private static final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	int getOrder() {
		return 3
	}

	@Override
	String getName() {
		return "agent-initializer"
	}

	public static void agentCheck(String sysProp) {
		String pAgent = System.getProperty(sysProp)

		if (pAgent != null) {
			for(String agent : pAgent.tokenize(',')) {
				agent = agent.trim()

				String extraParams = System.getProperty(sysProp + "." + agent)

				log.info("agent: {},{}", agent, extraParams);

				AgentLoader.findAgentInClasspath(agent, extraParams);
			}
		}
	}

	@Override
	String[] initialize(String[] args, String jumpClass) {
		agentCheck(AGENT_LOADING)

		return args
	}
}
