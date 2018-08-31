package unit8_class

package society {
  package professional {

    class Executive {
      private[professional] var workDetails = null
      private[society] var friends = null
      private[Executive] var secrets = null

      def help(another: Executive) {
        println(another.workDetails)
        println(another.secrets)
      }

    }


  }

}