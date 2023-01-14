Vagrant.configure("2") do |config|

    machines = [
        {
          :hostname => "lb.vm",
          :ip => "10.250.0.10",
          :ssh_host_port => 2200
        },
        {
            :hostname => "app1.vm",
            :ip => "10.250.0.100",
            :ssh_host_port => 2201
        },
        {
            :hostname => "app2.vm",
            :ip => "10.250.0.101",
            :ssh_host_port => 2202
        },
        {
            :hostname => "app3.vm",
            :ip => "10.250.0.102",
            :ssh_host_port => 2203
        }
    ]
  
    box_image = ENV["VAGRANT_IMAGE"] || "centos-vagrant:7"

    machines.each do |machine|
      config.vm.define machine[:hostname] do |app|
        config.vm.provider "docker" do |d|
          d.image = box_image
          d.has_ssh = true
          d.remains_running = true
          d.create_args = ["--privileged"]
        end
        app.vm.hostname = machine[:hostname]
        app.vm.network "private_network", ip: machine[:ip]
        app.vm.network "forwarded_port", guest: 22, host: machine[:ssh_host_port], id: "ssh"
        if machine[:hostname] == "lb.vm"
            app.vm.network "forwarded_port", guest: 80, host: 8080
        end
      end
    end

  end