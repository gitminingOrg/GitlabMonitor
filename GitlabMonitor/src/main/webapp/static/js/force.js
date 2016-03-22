/**
 * 
 */
function force(div,width,height,fdata) {
	/*xdata = {
	          nodes: [
	                  {size: 10, name:"dtran320"},
	                  {size: 5,  name:"adamcrume"},
	                  {size: 2,  name:"danielpacak"},
	                  {size: 3,  name:"chriswebster"},
	                  {size: 30, name:"VWoeltjen"},
	                  {size: 40, name:"DanBerrios"},
	                  {size: 20, name:"custardtart"},
	                  {size: 15, name:"kptran"},
	                  {size: 30, name:"bhong"},
	                  {size: 7,  name:"phallbic"},
	                  {size: 17, name:"alpearson"},
	                 ],
	        links: [
	                  {source: 0,target: 1},
	                  {source: 0,target: 2},
	                  {source: 0,target: 7},
	                  {source: 0,target: 10},		
	                  {source: 1,target: 0},
	                  {source: 1,target: 5},
	                  {source: 0,target: 6},
	                  {source: 3,target: 0},
	                  {source: 0,target: 6},
	                  {source: 3,target: 9},
	                  {source: 4,target: 5},
	                  {source: 4,target: 7},
	                  {source: 4,target: 8},
	                  {source: 5,target: 8},
	                  {source: 6,target: 7},
	                  {source: 6,target: 8},
	                  {source: 6,target: 10},
	                  {source: 7,target: 10},
	                  {source: 9,target: 10},
	                ]
	        }*/
	data = eval("("+fdata+")");

	var mouseOverFunction = function(d) {
	  var circle = d3.select(this);

	  node
	    .transition(500)
	      .style("opacity", function(o) {
	        return isConnected(o, d) ? 1.0 : 0.2 ;
	      });

	  link
	    .transition(500)
	      .style("stroke-opacity", function(o) {
	        return o.source === d || o.target === d ? 1 : 0.2;
	      });

	  circle
	    .transition(500)
	      .attr("r", function(){ return 1.4 * node_radius(d)});
	}

	var mouseOutFunction = function() {
	  var circle = d3.select(this);

	  node
	    .transition(500)
	  	  .style("opacity", 1);
	  link
	    .transition(500)
	  	  .style("stroke-opacity", 1);

	  circle
	    .transition(500)
	      .attr("r", node_radius);
	}

	function isConnected(a, b) {
	    return isConnectedAsTarget(a, b) || isConnectedAsSource(a, b) 

	|| a.index == b.index;
	}

	function isConnectedAsSource(a, b) {
	    return linkedByIndex[a.index + "," + b.index];
	}

	function isConnectedAsTarget(a, b) {
	    return linkedByIndex[b.index + "," + a.index];
	}

	function isEqual(a, b) {
	    return a.index == b.index;
	}

	function tick() {
	  link
	    .attr("x1", function(d) { return d.source.x; })
	    .attr("y1", function(d) { return d.source.y; })
	    .attr("x2", function(d) { return d.target.x; })
	    .attr("y2", function(d) { return d.target.y; });

	  node
	    .attr("transform", function(d) { return "translate(" + d.x + 

	"," + d.y + ")"; });
	}

	function node_radius(d) { return Math.pow(40.0 * d.size, 1/3); }

	var width = width;
	var height = height;

	var nodes = data.nodes
	var links = data.links
	var strokewidth = data.widths

	var force = d3.layout.force()
	              .nodes(nodes)
	              .links(links)
	              .charge(-3000)
	              .friction(0.6)
	              .gravity(0.6)
	              .linkDistance(200)
	              .size([width,height])
	              .start();

	var linkedByIndex = {};
	links.forEach(function(d) {
	  linkedByIndex[d.source.index + "," + d.target.index] = true;
	});

	var svg = d3.select(div).append("svg")
	            .attr("width", width)
	            .attr("height", height);
	
	var colors=d3.scale.category20();   
    
     
	var link = svg.selectAll("line")
	              .data(links)
	            .enter().append("line")
	            .style("stroke",function(d){
	                return colors(d);    
	            })
	            .style("stroke-width",function(d,i){
	            	return strokewidth[i]*strokewidth[i] / 10000;
	            });
	            
	var node = svg.selectAll(".node")
	              .data(nodes)
	            .enter().append("g")
	              .attr("class", "node")
	              .call(force.drag);

	node
	  .append("circle")
	  .attr("r", node_radius)
	   .style("fill",function(d,i){     
	               return colors(i);  
	           })
	  .on("mouseover", mouseOverFunction)
	  .on("mouseout", mouseOutFunction)
	  //.on("click",function(d){info(d.name);});

	 node.append("text")  
		               .attr("x", 12)  
		               .attr("dy", ".35em")  
		               .text(function(d) { return d.name; });  

	force
	  .on("tick",tick);
}