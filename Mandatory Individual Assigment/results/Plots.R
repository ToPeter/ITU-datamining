library(ggplot2)
setwd("C:/Users/lucas/Documents/gitRepos/Data-Mining/DM/Mandatory Individual Assigment/results")
neighboursData <- read.csv("KNeighbours.csv")
str(neighboursData)
plot(neighboursData)
summary(neighboursData)

clusters <- read.csv("clusters.csv")
str(clusters)
summary(clusters)
qplot(shoeSize, height, colour = cluster, shape = gender,
      data = clusters)

