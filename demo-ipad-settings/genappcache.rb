source = ENV['SOURCE'] || raise("SOURCE=... needed")
file_types = "css,js,html,jpeg,jpg,png,gif"


puts <<-eos
CACHE MANIFEST
# #{Time.now}

CACHE:
eos

Dir[File.join(source, "/**/*.{#{file_types}}")].each do |path|
   puts path.gsub(/#{source}\//, "") unless path =~ /^#{source}\/WEB-INF\//
end

puts <<-eos

NETWORK:
*
eos