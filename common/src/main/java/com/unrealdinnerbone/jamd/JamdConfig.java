package com.unrealdinnerbone.jamd;

import java.util.List;

public record JamdConfig(boolean dynamicOreAddition, List<String> blackListedOres, List<String> addValues) {
}
