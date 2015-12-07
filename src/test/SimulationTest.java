/**
 * Copyright 2015 Javier Montero,Diego Martin, Javier Rabanillo

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package test;

import main.Simulation;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class SimulationTest {

    @Test
    public void minimumListMethodWorks(){
        Simulation sim = new Simulation();

        sim.waiting_planes_landing = Collections.singletonList(10.00);
        sim.waiting_planes_guidance = Collections.singletonList(20.00);
        sim.waiting_planes_terminal = Collections.singletonList(30.00);
        sim.waiting_planes_takeoff = Collections.singletonList(40.00);

        Assert.assertEquals(1,sim.checkMinimumList());
    }
}
